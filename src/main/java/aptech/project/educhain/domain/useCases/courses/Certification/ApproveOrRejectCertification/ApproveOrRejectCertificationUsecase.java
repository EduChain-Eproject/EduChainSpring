package aptech.project.educhain.domain.useCases.courses.Certification.ApproveOrRejectCertification;

import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.stereotype.Component;
import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Certification;
import aptech.project.educhain.data.entities.courses.CertificationStatus;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.CertificationRepository;
import aptech.project.educhain.data.repositories.courses.UserHomeworkRepository;
import aptech.project.educhain.data.serviceImpl.web3.Web3Service;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CertificationDTO;
import aptech.project.educhain.domain.useCases.courses.Certification.ApproveOrRejectCertification.ApproveOrRejectCertificationParams.TeacherUpdatingCertificationStatus;

import java.math.BigInteger;
import java.util.stream.Collectors;

@Component
public class ApproveOrRejectCertificationUsecase
        implements Usecase<CertificationDTO, ApproveOrRejectCertificationParams> {
    @Autowired
    CertificationRepository certificationRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    Web3Service web3Service;

    @Autowired
    UserHomeworkRepository userHomeworkRepository;

    @Override
    public AppResult<CertificationDTO> execute(ApproveOrRejectCertificationParams params) {
        try {
            var CertificationOptional = certificationRepository.findById(params.getCertificationId());

            if (!CertificationOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Certification not found"));
            }

            Certification certification = CertificationOptional.get();

            if (certification.getCourse().getTeacher().getId() != params.getTeacherId()) {
                return AppResult.failureResult(new Failure("You have no permission to approve this Certification!"));
            }

            if (params.getUpdatingCertificationStatus() == TeacherUpdatingCertificationStatus.APPROVE) {
                certification.setStatus(CertificationStatus.CERTIFIED);
            } else if (params.getUpdatingCertificationStatus() == TeacherUpdatingCertificationStatus.REJECT) {
                certification.setStatus(CertificationStatus.REJECTED);
            }

            if (params.getComments() != null) {
                certification.setComments(params.getComments());
            }
            certification.setIssueDate(java.time.LocalDateTime.now());

            var user = certification.getUser();

            if (user.getWalletAddress() == null) {
                return AppResult.failureResult(new Failure("User has no wallet address"));
            } else if (certification.getStatus().equals(CertificationStatus.CERTIFIED)) {
                var homeworkIds = certification.getCourse().getChapters().stream()
                        .flatMap(chapter -> chapter.getLessons().stream()
                                .flatMap(lesson -> lesson.getHomeworks().stream()
                                        .map(homework -> homework.getId())))
                        .collect(Collectors.toList());

                var grades = homeworkIds.stream()
                        .map(homeworkId -> {
                            var userHomework = userHomeworkRepository.findByUserIdAndHomeworkId(user.getId(),
                                    homeworkId);
                            if (userHomework.isPresent() && userHomework.get().getGrade() != null) {
                                return userHomework.get().getGrade().doubleValue();
                            } else {
                                return 0.0;
                            }
                        })
                        .collect(Collectors.toList());

                double averageGrade = grades.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0);

                AppResult<TransactionReceipt> result = web3Service.issueCertification(
                        user.getWalletAddress(),
                        BigInteger.valueOf(
                                certification.getIssueDate().atZone(java.time.ZoneId.systemDefault()).toEpochSecond()),
                        BigInteger.valueOf(certification.getCourse().getTeacher().getId()),
                        BigInteger.valueOf(certification.getUser().getId()),
                        BigInteger.valueOf(certification.getCourse().getId()),
                        averageGrade > 75 ? BigInteger.valueOf(2)
                                : averageGrade > 60 ? BigInteger.ONE : BigInteger.ZERO);

                if (result.isFailure()) {
                    return AppResult.failureResult(
                            new Failure("Failed to issue certification: " + result.getFailure().getMessage()));
                } else {
                    TransactionReceipt receipt = result.getSuccess();
                    var event = web3Service.getCertificationIssuedEvent(receipt);
                    certification.setNftTokenId(event.certificationId.toString());
                    certification.setTransactionHash(receipt.getTransactionHash());
                }
            }

            certificationRepository.save(certification);

            var certificationDTO = modelMapper.map(certification, CertificationDTO.class);
            certificationDTO.setUserDto(modelMapper.map(user, UserDTO.class));

            return AppResult.successResult(certificationDTO);

        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to approve an Certification: " + e.getMessage()));
        }
    }
}
