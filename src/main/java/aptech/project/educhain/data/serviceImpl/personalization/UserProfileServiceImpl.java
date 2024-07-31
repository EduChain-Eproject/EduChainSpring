package aptech.project.educhain.data.serviceImpl.personalization;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.repositories.courses.UserHomeworkRepository;
import aptech.project.educhain.domain.dtos.UserProfile.UserProfileDTO;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import aptech.project.educhain.domain.services.personalization.UserProfileService;
import aptech.project.educhain.domain.useCases.personalization.user_homework.list_userhomework.ListUserHomeworkParams;
import aptech.project.educhain.domain.useCases.personalization.user_homework.list_userhomework.ListUserHomeworkUseCase;
import aptech.project.educhain.domain.useCases.personalization.user_homework.take_one_userhomework.TakeOneUserHomeworkParams;
import aptech.project.educhain.domain.useCases.personalization.user_homework.take_one_userhomework.TakeOneUserHomeworkUseCase;
import aptech.project.educhain.domain.useCases.personalization.user_profile.get_profile_usecase.GetUserProfileParam;
import aptech.project.educhain.domain.useCases.personalization.user_profile.get_profile_usecase.GetUserProfileUseCase;
import aptech.project.educhain.domain.useCases.personalization.user_award.take_one_award.TakeOneAwardParams;
import aptech.project.educhain.domain.useCases.personalization.user_award.take_one_award.TakeOneAwardUseCase;
import aptech.project.educhain.domain.useCases.personalization.user_profile.update_profile_usecase.UpdateUserProfileParam;
import aptech.project.educhain.domain.useCases.personalization.user_profile.update_profile_usecase.UpdateUserProfileUseCase;
import aptech.project.educhain.domain.useCases.personalization.user_award.get_user_award_userId.UserAwardParams;
import aptech.project.educhain.domain.useCases.personalization.user_award.get_user_award_userId.UserAwardUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    ListUserHomeworkUseCase listUserHomeworkUseCase;
    @Autowired
    GetUserProfileUseCase getUserProfileUseCase;

    @Autowired
    UpdateUserProfileUseCase userProfileUseCase;

    @Autowired
    UserAwardUseCase userAwardUseCase;
    @Autowired
    TakeOneAwardUseCase takeOneAwardUseCase;

    @Autowired
    TakeOneUserHomeworkUseCase takeOneUserHomeworkUseCase;
    @Override
    public AppResult<UserProfileDTO> getUserProfile(GetUserProfileParam params) {
        return getUserProfileUseCase.execute(params);
    }
    @Override
    public AppResult<UserProfileDTO> updateProfile(UpdateUserProfileParam params) {
        return userProfileUseCase.execute(params);
    }

    @Override
    public AppResult<Page<AwardDTO>> listAwardByUserId(UserAwardParams params) {
        return userAwardUseCase.execute(params);
    }

    @Override
    public AppResult<AwardDTO> awardByUserId(TakeOneAwardParams params) {
        return takeOneAwardUseCase.execute(params);
    }

    @Override
    public AppResult<Page<UserHomeworkDTO>> listHomeworkdByUserId(ListUserHomeworkParams params) {
        return listUserHomeworkUseCase.execute(params);
    }

    @Override
    public AppResult<UserHomeworkDTO> takeOneUserHomework(TakeOneUserHomeworkParams params) {
        return takeOneUserHomeworkUseCase.execute(params);
    }

}
