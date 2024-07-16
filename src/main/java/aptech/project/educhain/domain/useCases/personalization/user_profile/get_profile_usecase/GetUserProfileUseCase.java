package aptech.project.educhain.domain.useCases.personalization.user_profile.get_profile_usecase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.domain.dtos.UserProfile.UserProfileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUserProfileUseCase implements Usecase<UserProfileDTO, GetUserProfileParam> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public AppResult<UserProfileDTO> execute(GetUserProfileParam params) {
        try{
            User user = authUserRepository.findUserWithEmail(params.getEmail());
            UserProfileDTO userProfileDTO = modelMapper.map(user,UserProfileDTO.class);
            return AppResult.successResult(userProfileDTO);
        }
        catch (Exception e){
            return AppResult.failureResult(new Failure("Fail to get your profile"+ e.getMessage()));
        }
    }
}
