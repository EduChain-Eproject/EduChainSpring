package aptech.project.educhain.domain.useCases.personalization.user_course.add_user_course;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.courses.UserCourse;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.data.repositories.courses.UserCourseRepository;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import aptech.project.educhain.domain.useCases.personalization.user_course.get_all_user_course.UserCourseParams;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AddUserCourseUseCase implements Usecase<UserCourseDTO, AddUserCourseParams> {
    @Autowired
    UserCourseRepository userCourseRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    CourseRepository courseRepository;
    @Override
    @Transactional
    public AppResult<UserCourseDTO> execute(AddUserCourseParams params) {
        try{
            //save new userCourse
            User user =  authUserRepository.findUserWithId(params.getStudent_id());
            Course course = courseRepository.findCourseWithId(params.getCourse_id());
            UserCourse userCourse = new UserCourse();
            userCourse.setUser(user);
            userCourse.setCourse(course);
            userCourse.setProgress(BigDecimal.valueOf(0));
            userCourse.setEnrollmentDate(new Timestamp(System.currentTimeMillis()));
            userCourse.setCompletionStatus(UserCourse.CompletionStatus.NOT_STARTED);
            UserCourse newUserCourse = userCourseRepository.saveAndFlush(userCourse);
            //find some value
            User teacher = newUserCourse.getCourse().getTeacher();
            UserCourseDTO userCourseDTO = createUserCourseDTO(newUserCourse, teacher);
            return AppResult.successResult(userCourseDTO);
        }
        catch (Exception e){
            return AppResult.failureResult(new Failure("Fail to create user-course"));
        }

    }

    private UserCourseDTO createUserCourseDTO(UserCourse newUserCourse, User teacher) {
        List<Category> categoryList = newUserCourse.getCourse().getCategories();

        // Map Category entities to CategoryDTOs
        List<CategoryDTO> categoryDTOList = categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());

        // Map other properties
        UserCourseDTO userCourseDTO = new UserCourseDTO();
        userCourseDTO.setTeacherName(teacher.getFirstName() + " " + teacher.getLastName());
        userCourseDTO.setTeacherEmail(teacher.getEmail());
        userCourseDTO.setTitle(newUserCourse.getCourse().getTitle());
        userCourseDTO.setEnrollmentDate(newUserCourse.getEnrollmentDate());
        userCourseDTO.setPrice(newUserCourse.getCourse().getPrice());
        userCourseDTO.setCompletionStatus(newUserCourse.getCompletionStatus());
        userCourseDTO.setCategoryList(categoryDTOList);

        return userCourseDTO;
    }

}
