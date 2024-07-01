package aptech.project.educhain.data.serviceImpl.user_profile;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.UserProfile.UserProfileDTO;
import aptech.project.educhain.domain.services.user_profile.IUserProfile;
import aptech.project.educhain.domain.useCases.account.user_profile.get_profile_usecase.GetUserProfileParam;
import aptech.project.educhain.domain.useCases.account.user_profile.get_profile_usecase.GetUserProfileUseCase;
import aptech.project.educhain.domain.useCases.account.user_profile.update_profile_usecase.UpdateUserProfileParam;
import aptech.project.educhain.domain.useCases.account.user_profile.update_profile_usecase.UpdateUserProfileUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements IUserProfile {

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
