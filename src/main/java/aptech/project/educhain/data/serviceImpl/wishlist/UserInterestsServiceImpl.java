package aptech.project.educhain.data.serviceImpl.wishlist;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import aptech.project.educhain.domain.services.UserInterests.UserInterestsService;
import aptech.project.educhain.domain.useCases.account.wishlist.add_to_user_interests.AddToUserInterestsParams;
import aptech.project.educhain.domain.useCases.account.wishlist.add_to_user_interests.AddToUserInterestsUseCase;
import aptech.project.educhain.domain.useCases.account.wishlist.delete_from_user_interests.DeleteUserInterestsParams;
import aptech.project.educhain.domain.useCases.account.wishlist.delete_from_user_interests.DeleteUserInterestsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInterestsServiceImpl implements UserInterestsService {
    @Autowired
    AddToUserInterestsUseCase addToWishListUseCase;
//    @Autowired
//    DeleteUserInterestsUseCase deleteUserInterestsUseCase;

    public AppResult<UserInterestsDTO> addWishList(AddToUserInterestsParams params) {
        return addToWishListUseCase.execute(params);
    }

//    public AppResult<UserInterestsDTO> deleteWishList(DeleteUserInterestsParams params){
//        return addToWishListUseCase.execute(params);
//    }
}
