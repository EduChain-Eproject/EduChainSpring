package aptech.project.educhain.domain.services.personalization;

import org.springframework.data.domain.Page;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import aptech.project.educhain.domain.useCases.personalization.user_course.add_user_course.AddUserCourseParams;
import aptech.project.educhain.domain.useCases.personalization.user_course.get_all_user_course.UserCourseParams;
import aptech.project.educhain.domain.useCases.personalization.user_course.get_user_course.GetUserCourseParams;

import java.util.List;

public interface UserCourseService {
     AppResult<Page<UserCourseDTO>> getAllUserCourseWithParams(UserCourseParams params);

     AppResult<UserCourseDTO> addUserCourseWithParams(AddUserCourseParams params);

     AppResult<UserCourseDTO> getUserCourse(GetUserCourseParams params);
}
