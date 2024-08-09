package aptech.project.educhain.data.serviceImpl.personalization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import aptech.project.educhain.domain.services.personalization.UserInterestsService;
import aptech.project.educhain.domain.useCases.personalization.user_interest.add_to_user_interests.AddToUserInterestsParams;
import aptech.project.educhain.domain.useCases.personalization.user_interest.add_to_user_interests.AddToUserInterestsUseCase;
import aptech.project.educhain.domain.useCases.personalization.user_interest.delete_from_user_interests.DeleteUserInterestsParams;
import aptech.project.educhain.domain.useCases.personalization.user_interest.delete_from_user_interests.DeleteUserInterestsUseCase;
import aptech.project.educhain.domain.useCases.personalization.user_interest.get_all_user_interest.GetUserInterestByUserIdParams;
import aptech.project.educhain.domain.useCases.personalization.user_interest.get_all_user_interest.GetUserInterestUseCase;

@Service
public class UserInterestsServiceImpl implements UserInterestsService {

    @Autowired
    AddToUserInterestsUseCase addToWishListUseCase;
    @Autowired
    GetUserInterestUseCase getUserInterestUseCase;
    @Autowired
    DeleteUserInterestsUseCase deleteUserInterestsUseCase;

    @Override
    public AppResult<UserInterestsDTO> addUserInterest(AddToUserInterestsParams params) {
        return addToWishListUseCase.execute(params);
    }

    @Override
    public AppResult<Boolean> deleteUserInterest(DeleteUserInterestsParams params) {
        return deleteUserInterestsUseCase.execute(params);
    }

    @Override
    public AppResult<Page<UserInterestsDTO>> getAlluserInterestByUserId(GetUserInterestByUserIdParams params) {
        return getUserInterestUseCase.execute(params);
    }

}
