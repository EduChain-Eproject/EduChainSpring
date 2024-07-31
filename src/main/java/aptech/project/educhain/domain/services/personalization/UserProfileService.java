package aptech.project.educhain.domain.services.personalization;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.UserProfile.UserProfileDTO;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import aptech.project.educhain.domain.useCases.personalization.user_homework.list_userhomework.ListUserHomeworkParams;
import aptech.project.educhain.domain.useCases.personalization.user_homework.take_one_userhomework.TakeOneUserHomeworkParams;
import aptech.project.educhain.domain.useCases.personalization.user_profile.get_profile_usecase.GetUserProfileParam;
import aptech.project.educhain.domain.useCases.personalization.user_award.take_one_award.TakeOneAwardParams;
import aptech.project.educhain.domain.useCases.personalization.user_profile.update_profile_usecase.UpdateUserProfileParam;
import aptech.project.educhain.domain.useCases.personalization.user_award.get_user_award_userId.UserAwardParams;
import org.springframework.data.domain.Page;

public interface UserProfileService {
    public AppResult<UserProfileDTO> getUserProfile(GetUserProfileParam params);

     AppResult<UserProfileDTO> updateProfile(UpdateUserProfileParam params);

    public AppResult<Page<AwardDTO>> listAwardByUserId(UserAwardParams params);
    AppResult<AwardDTO> awardByUserId(TakeOneAwardParams params);

    AppResult<Page<UserHomeworkDTO>> listHomeworkdByUserId(ListUserHomeworkParams params);
    AppResult<UserHomeworkDTO> takeOneUserHomework(TakeOneUserHomeworkParams params);

}
