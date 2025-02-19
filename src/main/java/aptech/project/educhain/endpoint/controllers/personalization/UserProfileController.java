package aptech.project.educhain.endpoint.controllers.personalization;

import java.util.HashMap;
import java.util.Map;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.data.serviceImpl.common.UploadPhotoService;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.domain.dtos.UserProfile.UserProfileDTO;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.services.personalization.UserProfileService;
import aptech.project.educhain.domain.useCases.personalization.user_profile.get_profile_usecase.GetUserProfileParam;
import aptech.project.educhain.domain.useCases.personalization.user_profile.update_profile_usecase.UpdateUserProfileParam;
import aptech.project.educhain.domain.useCases.personalization.user_profile.update_wallet_addres.UpdateWalletAddressParams;
import aptech.project.educhain.endpoint.requests.personaliztion.user_profile.UpdateUserRequest;
import aptech.project.educhain.endpoint.requests.personaliztion.user_profile.UpdateWalletAddressRequest;
import aptech.project.educhain.endpoint.responses.common.UserProfileResponse;
import aptech.project.educhain.endpoint.responses.common.WalletAddressUpdatedResponse;
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

    @Autowired
    UploadPhotoService uploadPhotoService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("getUser")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return new ResponseEntity<>(new ApiError("cant find token in your header"),
                    HttpStatus.BAD_REQUEST);
        }
        String newToken = token.substring(7);
        var email = iJwtService.extractUserName(newToken);
        if (email == null) {
            return new ResponseEntity<>(new ApiError("invalid token from header"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = iAuthService.findUserByEmail(email);
        UserDTO userDtoResponse = modelMapper.map(user, UserDTO.class);
        userDtoResponse.setCourseDtosParticipated(
                user
                        .getCoursesParticipated()
                        .stream()
                        .map((uc) -> modelMapper.map(uc, UserCourseDTO.class))
                        .toList());
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

    @PutMapping(value = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(HttpServletRequest request,
            @Valid @ModelAttribute UpdateUserRequest updateUserRequest,
            BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            ApiError apiError = new ApiError(errors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        String token = request.getHeader("Authorization");
        if (token == null) {
            return new ResponseEntity<>(new ApiError("Can't find token in your header"), HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/updateWalletAddress")
    public ResponseEntity<?> updateWalletAddress(HttpServletRequest request,
            @Valid @RequestBody UpdateWalletAddressRequest updateUserRequest,
            BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            ApiError apiError = new ApiError(errors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        String token = request.getHeader("Authorization");
        if (token == null) {
            return new ResponseEntity<>(new ApiError("Can't find token in your header"), HttpStatus.BAD_REQUEST);
        }
        String newToken = token.substring(7);
        var email = iJwtService.extractUserName(newToken);
        User user = iAuthService.findUserByEmail(email);

        UpdateWalletAddressParams params = modelMapper.map(updateUserRequest,
                UpdateWalletAddressParams.class);

        params.setUserId(user.getId());

        var result = userProfileService.updateWalletAddress(params);

        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), WalletAddressUpdatedResponse.class);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // list award by user id

    // @PostMapping("/list-award")
    // public ResponseEntity<?> getAwardsByUserId(@RequestBody UserAwardRequest
    // request) {
    // UserAwardParams userAwardParams =
    // modelMapper.map(request,UserAwardParams.class);
    //
    // AppResult<Page<AwardDTO>> result =
    // userProfileService.listAwardByUserId(userAwardParams);
    // if (result.isSuccess()) {
    // var res = result.getSuccess().map(awardDto -> modelMapper.map(awardDto,
    // UserAwardResponse.class));
    // return ResponseEntity.ok().body(res);
    // }
    // return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    // }

    // //get 1 award by user id
    // @PostMapping("/award")
    // public ResponseEntity<?> getAwardByUserIdAndAwardId(@RequestBody
    // TakeOneAwardRequest req) {
    // TakeOneAwardParams params = modelMapper.map(req,TakeOneAwardParams.class);
    // AppResult<AwardDTO> result = userProfileService.awardByUserId(params);
    // if (result.isSuccess()) {
    // AwardDTO awardDto = result.getSuccess();
    // return ResponseEntity.ok().body(awardDto);
    // }
    // return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    // }

}
