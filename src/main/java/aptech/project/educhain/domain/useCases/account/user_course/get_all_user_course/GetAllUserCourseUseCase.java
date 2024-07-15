package aptech.project.educhain.domain.useCases.account.user_course.get_all_user_course;

import org.springframework.beans.factory.annotation.Autowired;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.repositories.courses.UserCourseRepository;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;

public class GetAllUserCourseUseCase implements Usecase<UserCourseDTO, UserCourseParams> {
    @Autowired
    UserCourseRepository userCourseRepository;

    @Autowired

    @Override
    public AppResult<UserCourseDTO> execute(UserCourseParams params) {
        // find all usercourse base on id

        return null;
    }
}
