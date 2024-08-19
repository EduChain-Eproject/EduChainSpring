package aptech.project.educhain.endpoint.controllers.personalization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import aptech.project.educhain.domain.useCases.personalization.user_award.get_user_award_userId.UserAwardParams;
import aptech.project.educhain.domain.useCases.personalization.user_award.take_one_award.TakeOneAwardParams;
import aptech.project.educhain.domain.useCases.personalization.user_homework.list_userhomework.ListUserHomeworkParams;
import aptech.project.educhain.domain.useCases.personalization.user_homework.take_one_userhomework.TakeOneUserHomeworkParams;
import aptech.project.educhain.endpoint.requests.personaliztion.user_award.TakeOneAwardRequest;
import aptech.project.educhain.endpoint.requests.personaliztion.user_award.UserAwardRequest;
import aptech.project.educhain.endpoint.requests.personaliztion.user_homework.TakeOneUserHomeworkRequest;
import aptech.project.educhain.endpoint.requests.personaliztion.user_homework.UserHomeworkRequest;
import aptech.project.educhain.endpoint.responses.common.UserAwardResponse;
import aptech.project.educhain.endpoint.responses.common.UserHomeworkResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.domain.dtos.UserProfile.UserProfileDTO;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.services.personalization.UserProfileService;
import aptech.project.educhain.domain.useCases.personalization.user_profile.get_profile_usecase.GetUserProfileParam;
import aptech.project.educhain.domain.useCases.personalization.user_profile.update_profile_usecase.UpdateUserProfileParam;
import aptech.project.educhain.endpoint.requests.personaliztion.user_profile.UpdateUserRequest;
import aptech.project.educhain.endpoint.responses.common.UserProfileResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("COMMON")
public class UserProfileController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    IJwtService iJwtService;

    @Autowired
    IAuthService iAuthService;


    @GetMapping("getUser")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            //todo
            return new ResponseEntity<>(new ApiError("cant find token in your header"),
                    HttpStatus.BAD_REQUEST);
        }
        String newToken = token.substring(7);
        var email = iJwtService.extractUserName(newToken);
        if(email == null){
            //todo
            return new ResponseEntity<>(new ApiError("invalid token from header"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = iAuthService.findUserByEmail(email);
        UserDTO userDtoResponse = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDtoResponse);
    }

    @GetMapping("/get-user-profile/{email}")
    public ResponseEntity<?> getUserProfile(@PathVariable("email") String email) {
        GetUserProfileParam params = new GetUserProfileParam();
        params.setEmail(email);
        var result = userProfileService.getUserProfile(params);
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), UserProfileDTO.class);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        ApiError apiError = new ApiError(result.getFailure().getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(@Valid @ModelAttribute UpdateUserRequest updateUserRequest,
            BindingResult rs,HttpServletRequest request) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            ApiError apiError = new ApiError(errors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        String token = request.getHeader("Authorization");
        if (token == null) {
            //todo
            return new ResponseEntity<>(new ApiError("cant find token in your header"),
                    HttpStatus.BAD_REQUEST);
        }
        String newToken = token.substring(7);
        var email = iJwtService.extractUserName(newToken);
        User user = iAuthService.findUserByEmail(email);
        UpdateUserProfileParam updateUserProfileParam = modelMapper.map(updateUserRequest,
                UpdateUserProfileParam.class);

        updateUserProfileParam.setId(user.getId());
        var result = userProfileService.updateProfile(updateUserProfileParam);
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), UserProfileResponse.class);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        //todo
        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    //list award by user id

//    @PostMapping("/list-award")
//    public ResponseEntity<?> getAwardsByUserId(@RequestBody UserAwardRequest request) {
//        UserAwardParams userAwardParams = modelMapper.map(request,UserAwardParams.class);
//
//        AppResult<Page<AwardDTO>> result = userProfileService.listAwardByUserId(userAwardParams);
//        if (result.isSuccess()) {
//            var res = result.getSuccess().map(awardDto -> modelMapper.map(awardDto, UserAwardResponse.class));
//            return ResponseEntity.ok().body(res);
//        }
//        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
//    }


//    //get 1 award by user id
//    @PostMapping("/award")
//    public ResponseEntity<?> getAwardByUserIdAndAwardId(@RequestBody TakeOneAwardRequest req) {
//            TakeOneAwardParams params = modelMapper.map(req,TakeOneAwardParams.class);
//        AppResult<AwardDTO> result = userProfileService.awardByUserId(params);
//        if (result.isSuccess()) {
//            AwardDTO awardDto = result.getSuccess();
//            return ResponseEntity.ok().body(awardDto);
//        }
//        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
//    }




}

