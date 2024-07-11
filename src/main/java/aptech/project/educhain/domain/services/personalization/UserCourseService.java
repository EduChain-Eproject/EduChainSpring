package aptech.project.educhain.domain.services.personalization;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import aptech.project.educhain.domain.useCases.personalization.user_course.add_user_course.AddUserCourseParams;
import aptech.project.educhain.domain.useCases.personalization.user_course.get_all_user_course.UserCourseParams;
import org.springframework.data.domain.Page;

public interface UserCourseService {

     AppResult<Page<UserCourseDTO>> getAllUserCourseWithParams(UserCourseParams params);
     AppResult<UserCourseDTO> addUserCourseWithParams(AddUserCourseParams params);
}
