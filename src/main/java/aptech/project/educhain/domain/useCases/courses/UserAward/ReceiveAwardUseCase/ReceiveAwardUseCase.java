package aptech.project.educhain.domain.useCases.courses.UserAward.ReceiveAwardUseCase;

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

@Component
public class ReceiveAwardUseCase implements Usecase<AwardDTO, ReceiveAwardParams> {

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<AwardDTO> execute(ReceiveAwardParams params) {
        try {
            Optional<Award> awardOptional = awardRepository.findByUserIdAndHomeworkId(params.getUserId(),
                    params.getHomeworkId());
            if (!awardOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Award not found"));
            }

            Award award = awardOptional.get();

            if (!award.getStatus().equals(AwardStatus.APPROVED)) {
                return AppResult.failureResult(new Failure("Award is not approved"));
            }

            award.setStatus(AwardStatus.RECEIVED);
            awardRepository.save(award);

            return AppResult.successResult(modelMapper.map(award, AwardDTO.class));

        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to receive an award: " + e.getMessage()));
        }
    }
}
