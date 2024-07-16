package aptech.project.educhain.endpoint.controllers.personalization;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return null;
        }
        String newToken = token.substring(7);
        var email = iJwtService.extractUserName(newToken);
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
        return new ResponseEntity<>(result.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(@Valid @ModelAttribute UpdateUserRequest updateUserRequest,
            BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            // ObjectError
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
        UpdateUserProfileParam updateUserProfileParam = modelMapper.map(updateUserRequest,
                UpdateUserProfileParam.class);
        var result = userProfileService.updateProfile(updateUserProfileParam);
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), UserProfileResponse.class);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(result.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }
}
