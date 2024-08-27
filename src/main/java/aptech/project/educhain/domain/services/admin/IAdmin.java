package aptech.project.educhain.domain.services.admin;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.useCases.admin.block_or_unblock.BlockOrUnBlockParams;
import aptech.project.educhain.domain.useCases.admin.course_list.GetCourseListParams;
import aptech.project.educhain.domain.useCases.admin.get_userlist.GetListUserParams;
import org.springframework.data.domain.Page;

public interface IAdmin {

    AppResult<Page<UserDTO>> getListUser(GetListUserParams params);
    AppResult<Boolean> blocAndUnBlock(BlockOrUnBlockParams params);
    AppResult<Page<CourseDTO>> getCourseList(GetCourseListParams req);
}
