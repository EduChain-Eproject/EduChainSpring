package aptech.project.educhain.domain.useCases.personalization.GetUserCertificationsUsecase;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Certification;
import aptech.project.educhain.data.entities.courses.CertificationStatus;
import aptech.project.educhain.data.repositories.courses.CertificationRepository;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CertificationDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import org.springframework.stereotype.Component;

@Component
public class GetUserCertificationsUsecase implements Usecase<List<CertificationDTO>, Integer> {

    @Autowired
    CertificationRepository certificationRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<List<CertificationDTO>> execute(Integer userId) {
        try {
            List<Certification> certifications = certificationRepository.findByUserId(userId,
                    CertificationStatus.CERTIFIED);

            List<CertificationDTO> certificationDtos = certifications.stream().map(certification -> {
                CertificationDTO certificationDto = modelMapper.map(certification, CertificationDTO.class);
                certificationDto.setUserDto(modelMapper.map(certification.getUser(), UserDTO.class));
                certificationDto.setCourseDTO(modelMapper.map(certification.getCourse(), CourseDTO.class));
                return certificationDto;
            }).toList();

            return AppResult.successResult(certificationDtos);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to retrieve certifications: " + e.getMessage()));
        }
    }

}
