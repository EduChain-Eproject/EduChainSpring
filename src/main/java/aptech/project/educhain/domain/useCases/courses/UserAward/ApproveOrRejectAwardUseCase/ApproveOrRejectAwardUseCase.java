package aptech.project.educhain.domain.useCases.courses.UserAward.ApproveOrRejectAwardUseCase;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Award;
import aptech.project.educhain.data.entities.courses.AwardStatus;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.AwardRepository;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.useCases.courses.UserAward.ApproveOrRejectAwardUseCase.ApproveOrRejectAwardParams.TeacherUpdatingAwardStatus;

@Component
public class ApproveOrRejectAwardUseCase implements Usecase<AwardDTO, ApproveOrRejectAwardParams> {

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<AwardDTO> execute(ApproveOrRejectAwardParams params) {
        try {
            Optional<Award> awardOptional = awardRepository.findById(params.getAwardId());
            if (!awardOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Award not found"));
            }

            Award award = awardOptional.get();

            if (award.getHomework().getUser().getId() != params.getTeacherId()) {
                return AppResult.failureResult(new Failure("You have no permission to approve this award!"));
            }

            if (params.getUpdatingAwardStatus() == TeacherUpdatingAwardStatus.APPROVE) {
                award.setStatus(AwardStatus.APPROVED);
            } else if (params.getUpdatingAwardStatus() == TeacherUpdatingAwardStatus.REJECT) {
                award.setStatus(AwardStatus.REJECTED);
            }

            award.setReviewDate(LocalDateTime.now());
            awardRepository.save(award);

            return AppResult.successResult(modelMapper.map(award, AwardDTO.class));

        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to approve an award: " + e.getMessage()));
        }
    }
}
