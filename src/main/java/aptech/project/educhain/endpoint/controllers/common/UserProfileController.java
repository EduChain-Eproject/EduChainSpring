package aptech.project.educhain.endpoint.controllers.common;

import aptech.project.educhain.domain.dtos.UserProfile.UserProfileDTO;
import aptech.project.educhain.domain.services.user_profile.UserProfileService;
import aptech.project.educhain.domain.useCases.account.user_profile.get_profile_usecase.GetUserProfileParam;
import aptech.project.educhain.domain.useCases.account.user_profile.update_profile_usecase.UpdateUserProfileParam;
import aptech.project.educhain.endpoint.requests.accounts.user_profile.UpdateUserRequest;
import aptech.project.educhain.endpoint.responses.common.UserProfileResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("COMMON")
public class UserProfileController {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserProfileService userProfileService;

    @GetMapping("/get-user-profile/{email}")
    public ResponseEntity<?> getUserProfile(@PathVariable("email") String email){
        GetUserProfileParam params = new GetUserProfileParam();
        params.setEmail(email);
        var result =  userProfileService.getUserProfile(params);
        if(result.isSuccess()){
            var res = modelMapper.map(result.getSuccess(),UserProfileDTO.class);
            return  new ResponseEntity<>(res,HttpStatus.OK);
        }
        return  new ResponseEntity<>(result.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(@Valid @ModelAttribute UpdateUserRequest updateUserRequest, BindingResult rs){
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            // ObjectError
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
        UpdateUserProfileParam updateUserProfileParam = modelMapper.map(updateUserRequest,UpdateUserProfileParam.class);
        var result = userProfileService.updateProfile(updateUserProfileParam);
        if(result.isSuccess()){
            var res = modelMapper.map(result.getSuccess(), UserProfileResponse.class);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return  new ResponseEntity<>(result.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }
}
