package aptech.project.educhain.domain.useCases.courses.UserAward.GetUserAwardUseCase;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Award;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.AwardRepository;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;

@Component
public class GetUserAwardUseCase implements Usecase<AwardDTO, GetUserAwardParams> {

    @Autowired
    AwardRepository AwardRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<AwardDTO> execute(GetUserAwardParams params) {
        try {
            Optional<Award> AwardOptional = AwardRepository.findByUserIdAndHomeworkId(
                    params.userId,
                    params.homeworkId);

            if (AwardOptional.isPresent()) {

                Award Award = AwardOptional.get();

                AwardDTO AwardDTO = modelMapper.map(Award, AwardDTO.class);
                return AppResult.successResult(AwardDTO);
            } else {
                return AppResult.failureResult(new Failure("no award!"));
            }
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get user award: " + e.getMessage()));
        }
    }

}
