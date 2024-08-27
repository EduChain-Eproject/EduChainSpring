package aptech.project.educhain.endpoint.controllers.admin;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.domain.services.admin.IAdmin;
import aptech.project.educhain.domain.useCases.admin.block_or_unblock.BlockOrUnBlockParams;
import aptech.project.educhain.domain.useCases.admin.course_list.GetCourseListParams;
import aptech.project.educhain.domain.useCases.admin.get_userlist.GetListUserParams;
import aptech.project.educhain.endpoint.requests.admin.CourseListRequest;
import aptech.project.educhain.endpoint.requests.admin.GetUserListReq;
import aptech.project.educhain.endpoint.responses.admin.BlockOrUnBlocReq;
import aptech.project.educhain.endpoint.responses.admin.GetListUserResponse;
import com.cloudinary.api.exceptions.BadRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ADMIN")
public class AdminController {
    @Autowired
    IAuthService iAuthService;
    @Autowired
    IAdmin iAdmin;

    @Autowired
    ModelMapper modelMapper;

    //Get user list
        @PostMapping("/user-list")
    public ResponseEntity<?> getUserList(@RequestBody GetUserListReq req) {
            GetListUserParams getListUserParams = modelMapper.map(req, GetListUserParams.class);
            AppResult<Page<UserDTO>> result = iAdmin.getListUser(getListUserParams);
            if (result.isSuccess()) {
                Page<GetListUserResponse> res = result.getSuccess()
                        .map(dto -> {
                            GetListUserResponse response = modelMapper.map(dto, GetListUserResponse.class);

                            return response;
                        });
                return ResponseEntity.ok().body(res);
            }
            return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()),
                    HttpStatus.BAD_REQUEST);
        }


    @PostMapping("/blockOrUnblock")
    public ResponseEntity<?> blockOrUnblockUser(@RequestBody BlockOrUnBlocReq request) {
            BlockOrUnBlockParams params = modelMapper.map(request,BlockOrUnBlockParams.class);
        AppResult<Boolean> result = iAdmin.blocAndUnBlock(params);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.isSuccess());
        }
        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()),
                HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/course-list")
    public ResponseEntity<?> courseList(@RequestBody CourseListRequest req) {
        GetCourseListParams params = modelMapper.map(req,GetCourseListParams.class);
        AppResult<Page<CourseDTO>> result = iAdmin.getCourseList(params);
        if (result.isSuccess()) {

            return ResponseEntity.ok(result.getSuccess());
        }
        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()),
                HttpStatus.BAD_REQUEST);
    }

}

