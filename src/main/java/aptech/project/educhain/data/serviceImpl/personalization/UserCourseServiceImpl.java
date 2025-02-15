package aptech.project.educhain.data.serviceImpl.personalization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import aptech.project.educhain.domain.services.personalization.UserCourseService;
import aptech.project.educhain.domain.useCases.personalization.user_course.add_user_course.AddUserCourseParams;
import aptech.project.educhain.domain.useCases.personalization.user_course.add_user_course.AddUserCourseUseCase;
import aptech.project.educhain.domain.useCases.personalization.user_course.get_all_user_course.GetAllUserCourseUseCase;
import aptech.project.educhain.domain.useCases.personalization.user_course.get_all_user_course.UserCourseParams;
import aptech.project.educhain.domain.useCases.personalization.user_course.get_user_course.GetUserCourseParams;
import aptech.project.educhain.domain.useCases.personalization.user_course.get_user_course.GetUserCourseUseCase;

import java.util.List;

@Service
public class UserCourseServiceImpl implements UserCourseService {
    @Autowired
    GetAllUserCourseUseCase getAllUserCourseUseCase;

    @Autowired
    AddUserCourseUseCase addUserCourseUseCase;

    @Autowired
    GetUserCourseUseCase getUserCourseUseCase;

    @Override
    public AppResult<Page<UserCourseDTO>> getAllUserCourseWithParams(UserCourseParams params) {
        return getAllUserCourseUseCase.execute(params);
    }
    @Override
    public AppResult<UserCourseDTO> addUserCourseWithParams(AddUserCourseParams params) {
        return addUserCourseUseCase.execute(params);
    }

    @Override
    public AppResult<UserCourseDTO> getUserCourse(GetUserCourseParams params) {
        return getUserCourseUseCase.execute(params);
    }
}
