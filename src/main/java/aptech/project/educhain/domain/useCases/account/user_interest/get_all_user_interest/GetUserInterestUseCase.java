package aptech.project.educhain.domain.useCases.account.user_interest.get_all_user_interest;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.UserInterest;
import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.accounts.UserInterestRepository;

import aptech.project.educhain.data.repositories.courses.CourseCategoryRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetUserInterestUseCase implements Usecase<List<UserInterestsDTO>, GetUserInterestByUserIdParams> {
    @Autowired
    UserInterestRepository userInterestRepository;
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseCategoryRepository courseCategoryRepository;
    @Override
    public AppResult<List<UserInterestsDTO>> execute(GetUserInterestByUserIdParams params) {
        try{
            List<UserInterest> userInterestList = userInterestRepository.findByUserId(params.getUser_id());
            List<UserInterestsDTO> userInterestsDTOList = new ArrayList<UserInterestsDTO>();
            Course course = new Course();
            for(var interest : userInterestList){
                int id = interest.getCourse().getId();
                course = courseRepository.findCourseWithId(interest.getCourse().getId());
                UserInterestsDTO userInterestsDTO = new UserInterestsDTO();
                userInterestsDTO.setStudent_id(params.getUser_id());
                userInterestsDTO.setCourse_id(course.getId());
                userInterestsDTO.setTitle(course.getTitle());
                userInterestsDTO.setPrice(course.getPrice());
                userInterestsDTO.setTeacherName(course.getTeacher().getEmail());
                userInterestsDTO.setDescription(course.getDescription());
                //list cate
                int id2 = course.getId();
                var categoryList = courseCategoryRepository.getCategoryByCourseId(course.getId());
                userInterestsDTO.setCategoryList(categoryList);
                userInterestsDTOList.add(userInterestsDTO);
            }
            //list cate
           return  AppResult.successResult(userInterestsDTOList);
        }
        catch (Exception e){
            return AppResult.failureResult(new Failure("Fail to get user-interestsList"));
        }
    }
}
