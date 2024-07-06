package aptech.project.educhain.domain.useCases.account.wishlist.delete_from_user_interests;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.accounts.UserInterest;
import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.accounts.UserWishListRepository;
import aptech.project.educhain.data.repositories.courses.CourseCategoryRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import aptech.project.educhain.domain.useCases.account.wishlist.add_to_user_interests.AddToUserInterestsParams;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DeleteUserInterestsUseCase {
    @Autowired
    UserWishListRepository userWishListRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    CourseCategoryRepository courseCategoryRepository;

//    @Transactional
//    public AppResult<UserInterestsDTO> execute(AddToUserInterestsParams params) {
//        try{
//
//            return AppResult.successResult(wishListDTO);
//        }
//        catch (Exception e){
//            return AppResult.failureResult(new Failure("Failed to add: " + e.getMessage()));
//        }
//    }
}
