//package aptech.project.educhain.domain.useCases.account.wishlist.get_wishlist;
//
//import aptech.project.educhain.common.result.AppResult;
//import aptech.project.educhain.common.usecase.Usecase;
//import aptech.project.educhain.data.entities.accounts.UserInterest;
//import aptech.project.educhain.data.repositories.accounts.UserWishListRepository;
//import aptech.project.educhain.domain.dtos.courses.CourseDTO;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//
//public class GetWishListUseCase implements Usecase<CourseDTO,GetWishListUserIdParams> {
//    @Autowired
//    UserWishListRepository userWishListRepository;
//
//
//    @Override
//    public AppResult<CourseDTO> execute(GetWishListUserIdParams params) {
//        try{
//            List<UserInterest> userInterestList = userWishListRepository.findByUserId(params.getId());
//        }
//        catch (Exception e){
//
//        }
//    }
//}
