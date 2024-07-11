package aptech.project.educhain.data.serviceImpl.personalization;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.UserProfile.UserProfileDTO;
import aptech.project.educhain.domain.services.personalization.UserProfileService;
import aptech.project.educhain.domain.useCases.personalization.user_profile.get_profile_usecase.GetUserProfileParam;
import aptech.project.educhain.domain.useCases.personalization.user_profile.get_profile_usecase.GetUserProfileUseCase;
import aptech.project.educhain.domain.useCases.personalization.user_profile.update_profile_usecase.UpdateUserProfileParam;
import aptech.project.educhain.domain.useCases.personalization.user_profile.update_profile_usecase.UpdateUserProfileUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    GetUserProfileUseCase getUserProfileUseCase;

    @Autowired
    UpdateUserProfileUseCase userProfileUseCase;

    @Override
    public AppResult<UserProfileDTO> getUserProfile(GetUserProfileParam params) {
        return getUserProfileUseCase.execute(params);
    }
    @Override
    public AppResult<UserProfileDTO> updateProfile(UpdateUserProfileParam params) {
        return userProfileUseCase.execute(params);
    }

}
