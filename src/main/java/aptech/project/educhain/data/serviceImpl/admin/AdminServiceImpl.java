package aptech.project.educhain.data.serviceImpl.admin;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.services.admin.IAdmin;
import aptech.project.educhain.domain.useCases.admin.block_or_unblock.BlockOrUnBlockParams;
import aptech.project.educhain.domain.useCases.admin.block_or_unblock.BlockOrUnBlockUseCase;
import aptech.project.educhain.domain.useCases.admin.course_list.GetCourseListParams;
import aptech.project.educhain.domain.useCases.admin.course_list.GetCourseListUsecase;
import aptech.project.educhain.domain.useCases.admin.get_userlist.GetListUserParams;
import aptech.project.educhain.domain.useCases.admin.get_userlist.GetListUserUseCase;
import aptech.project.educhain.domain.useCases.personalization.user_course.get_all_user_course.UserCourseParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdmin {

    @Autowired
    GetListUserUseCase getListUserUseCase;
    @Autowired
    BlockOrUnBlockUseCase blockOrUnBlockUseCase;
    @Autowired
    GetCourseListUsecase getCourseListUsecase;
    @Override
    public AppResult<Page<UserDTO>> getListUser(GetListUserParams params) {
        return getListUserUseCase.execute(params);
    }

    @Override
    public AppResult<Boolean> blocAndUnBlock(BlockOrUnBlockParams params) {
        return blockOrUnBlockUseCase.execute(params);
    }

    @Override
    public AppResult<Page<CourseDTO>> getCourseList( GetCourseListParams req){
       return getCourseListUsecase.execute(req);
    }
}
