package aptech.project.educhain.endpoint.controllers.personalization;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.domain.services.personalization.UserCourseService;
import aptech.project.educhain.domain.useCases.personalization.user_course.add_user_course.AddUserCourseParams;
import aptech.project.educhain.domain.useCases.personalization.user_course.get_all_user_course.UserCourseParams;
import aptech.project.educhain.endpoint.requests.personaliztion.user_course.AddUserCourseRequest;
import aptech.project.educhain.endpoint.requests.personaliztion.user_course.UserCourseRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("STUDENT")
public class UserCourseController {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserCourseService userCourseService;
    //get user course list

    @PostMapping("/all-user-course")
    public ResponseEntity<?> takeAllUserCourse(@RequestBody UserCourseRequest req){
            UserCourseParams params = modelMapper.map(req,UserCourseParams.class);
        var userCourseDTO = userCourseService.getAllUserCourseWithParams(params);
        if(userCourseDTO.isSuccess()){
            return new  ResponseEntity<>(userCourseDTO.getSuccess(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiError(userCourseDTO.getFailure().getMessage()), HttpStatus.OK);
    }
    //add course to course list after payment
        @PostMapping("/add-user-course")
    public ResponseEntity<?> addUserCourse(@RequestBody AddUserCourseRequest req) {
        AddUserCourseParams addUserCourseParams = modelMapper.map(req,AddUserCourseParams.class);
        var userCourse = userCourseService.addUserCourseWithParams(addUserCourseParams);
        if(userCourse.isSuccess()){
            return new  ResponseEntity<>(userCourse.getSuccess(), HttpStatus.OK);
        }
            //todo
            return new ResponseEntity<>(new ApiError(userCourse.getFailure().getMessage()), HttpStatus.OK);
    }



}
