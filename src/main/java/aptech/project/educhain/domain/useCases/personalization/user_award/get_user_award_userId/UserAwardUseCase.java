package aptech.project.educhain.domain.useCases.personalization.user_award.get_user_award_userId;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class UserAwardUseCase implements Usecase<Page<AwardDTO>,UserAwardParams> {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AwardRepository awardRepository;
    @Override
    public AppResult<Page<AwardDTO>> execute(UserAwardParams params) {
        try {
            Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
            Page<Award> awards = awardRepository.findByUserId(params.getUserId(), pageable);
            Page<AwardDTO> awardDtos = awards.map(award -> {
                AwardDTO awardDto = modelMapper.map(award, AwardDTO.class);
                awardDto.setUserDto(modelMapper.map(award.getUser(), UserDTO.class));
                awardDto.setHomeworkDto(modelMapper.map(award.getHomework(), HomeworkDTO.class));
                return awardDto;
            });
            return AppResult.successResult(awardDtos);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to retrieve awards: " + e.getMessage()));
        }
    }
}
