package aptech.project.educhain.domain.services.personalization;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.UserProfile.UserProfileDTO;
import aptech.project.educhain.domain.useCases.personalization.user_profile.get_profile_usecase.GetUserProfileParam;
import aptech.project.educhain.domain.useCases.personalization.user_profile.update_profile_usecase.UpdateUserProfileParam;

public interface UserProfileService {
    public AppResult<UserProfileDTO> getUserProfile(GetUserProfileParam params);

     AppResult<UserProfileDTO> updateProfile(UpdateUserProfileParam params);
}
