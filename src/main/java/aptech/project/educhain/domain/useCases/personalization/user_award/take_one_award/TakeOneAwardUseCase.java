package aptech.project.educhain.domain.useCases.personalization.user_award.take_one_award;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Award;
import aptech.project.educhain.data.repositories.courses.AwardRepository;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class TakeOneAwardUseCase implements Usecase<AwardDTO , TakeOneAwardParams> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AwardRepository awardRepository;

    @Override
    public AppResult<AwardDTO> execute(TakeOneAwardParams params) {
        try {
            Optional<Award> optionalAward = awardRepository.findByUserIdAndAwardId(params.getStudent_id(), params.getAward_id());
            if (optionalAward.isPresent()) {
                Award award = optionalAward.get();
                AwardDTO awardDto = modelMapper.map(award, AwardDTO.class);
                awardDto.setUserDto(modelMapper.map(award.getUser(), UserDTO.class));
                awardDto.setHomeworkDto(modelMapper.map(award.getHomework(), HomeworkDTO.class));
                return AppResult.successResult(awardDto);
            } else {
                return AppResult.failureResult(new Failure("Award not found for given user ID and award ID"));
            }
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to retrieve award: " + e.getMessage()));
        }
    }
}
