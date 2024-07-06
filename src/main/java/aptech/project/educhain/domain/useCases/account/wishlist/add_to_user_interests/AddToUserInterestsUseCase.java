package aptech.project.educhain.domain.useCases.account.wishlist.add_to_user_interests;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.accounts.UserInterest;
import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.accounts.UserWishListRepository;
import aptech.project.educhain.data.repositories.courses.CourseCategoryRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AddToUserInterestsUseCase implements Usecase<UserInterestsDTO, AddToUserInterestsParams> {
    @Autowired
    UserWishListRepository userWishListRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    CourseCategoryRepository courseCategoryRepository;
    @Override
    @Transactional
    public AppResult<UserInterestsDTO> execute(AddToUserInterestsParams params) {
      try{
          //add find user find course
          User findUser = authUserRepository.findUserWithId(params.getUser_id());
          if(findUser.getRole().name().equals("TEACHER")){
              return AppResult.failureResult(new Failure("You don't have permission to do this action"));
          }
          Course findCourse = courseRepository.findCourseWithId(params.getCourse_id());
          // map data
          UserInterest userInterest = new UserInterest();
          userInterest.setCourse(findCourse);
          userInterest.setUser(findUser);

          UserInterestsDTO wishListDTO = new UserInterestsDTO();
          wishListDTO.setDescription(findCourse.getDescription());
          wishListDTO.setTitle(findCourse.getDescription());
          wishListDTO.setTeacherName(findCourse.getTeacher().getEmail());
          wishListDTO.setCourse_id(findCourse.getId());
          wishListDTO.setStudent_id(findUser.getId());
          // take out category list by using course id
        // List<Category> listcategory = courseCategoryRepository.getCategoryByCourseId(findCourse.getId());
          List<Category> listcategory = findCourse.getCategories();
          wishListDTO.setCategoryList(listcategory);
          //result
          return AppResult.successResult(wishListDTO);
      }
      catch (Exception e){
          return AppResult.failureResult(new Failure("Failed to add: " + e.getMessage()));
      }
    }
}
