package aptech.project.educhain.domain.services.user_profile;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.UserProfile.UserProfileDTO;
import aptech.project.educhain.domain.useCases.account.user_profile.get_profile_usecase.GetUserProfileParam;
import aptech.project.educhain.domain.useCases.account.user_profile.update_profile_usecase.UpdateUserProfileParam;

public interface IUserProfile {
    public AppResult<UserProfileDTO> getUserProfile(GetUserProfileParam params);

     AppResult<UserProfileDTO> updateProfile(UpdateUserProfileParam params);
}
