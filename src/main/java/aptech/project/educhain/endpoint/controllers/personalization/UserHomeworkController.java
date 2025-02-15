package aptech.project.educhain.endpoint.controllers.personalization;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.services.personalization.UserProfileService;
import aptech.project.educhain.domain.useCases.personalization.user_homework.list_userhomework.ListUserHomeworkParams;
import aptech.project.educhain.domain.useCases.personalization.user_homework.take_one_userhomework.TakeOneUserHomeworkParams;
import aptech.project.educhain.endpoint.requests.personaliztion.user_homework.TakeOneUserHomeworkRequest;
import aptech.project.educhain.endpoint.requests.personaliztion.user_homework.UserHomeworkRequest;
import aptech.project.educhain.endpoint.responses.common.UserHomeworkResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("STUDENT")
public class UserHomeworkController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserProfileService userProfileService;
    @Autowired
    IJwtService iJwtService;

    @Autowired
    IAuthService iAuthService;

    @PostMapping("/list-homework")
    public ResponseEntity<?> listUserHomeWork(HttpServletRequest request, @RequestBody UserHomeworkRequest req) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return new ResponseEntity<>(new ApiError("cant find token in your header"),
                    HttpStatus.BAD_REQUEST);
        }
        String newToken = token.substring(7);
        var email = iJwtService.extractUserName(newToken);
        User user = iAuthService.findUserByEmail(email);
        ListUserHomeworkParams params = modelMapper.map(req,ListUserHomeworkParams.class);
        params.setUserId(user.getId());
        AppResult<Page<UserHomeworkDTO>> result = userProfileService.listHomeworkdByUserId(params);
        if (result.isSuccess()) {
            var res = result.getSuccess().map(userHomeworkDTO -> modelMapper.map(userHomeworkDTO, UserHomeworkResponse.class));
            return ResponseEntity.ok().body(res);
        }

        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()),
                HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/getOneUserHomework")
    public ResponseEntity<?> oneUserHomework(@RequestBody TakeOneUserHomeworkRequest req) {
        TakeOneUserHomeworkParams params = modelMapper.map(req, TakeOneUserHomeworkParams.class);
        var result = userProfileService.takeOneUserHomework(params);
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), UserHomeworkResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
