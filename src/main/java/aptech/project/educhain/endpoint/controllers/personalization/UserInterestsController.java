package aptech.project.educhain.endpoint.controllers.personalization;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.services.personalization.UserInterestsService;
import aptech.project.educhain.domain.useCases.personalization.user_interest.add_to_user_interests.AddToUserInterestsParams;
import aptech.project.educhain.domain.useCases.personalization.user_interest.delete_from_user_interests.DeleteUserInterestsParams;
import aptech.project.educhain.domain.useCases.personalization.user_interest.get_all_user_interest.GetUserInterestByUserIdParams;
import aptech.project.educhain.endpoint.requests.personaliztion.user_interests.AddOrDeleteUserInterestRequest;
import aptech.project.educhain.endpoint.requests.personaliztion.user_interests.UserInterestRequest;
import aptech.project.educhain.endpoint.responses.common.UserInterestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("STUDENT")
public class UserInterestsController {
    @Autowired
    private IAuthService iAuthService;
    @Autowired
    public UserInterestsService userInterestsService;
    @Autowired
    public ModelMapper modelMapper;
    @Autowired
    private IJwtService iJwtService;
    @PostMapping("/add-to-wishlist")
    public ResponseEntity<?> addToWishList(@Valid @RequestBody AddOrDeleteUserInterestRequest req, BindingResult rs){
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(new ApiError(errors), HttpStatus.BAD_REQUEST);
        }
        //add
       AddToUserInterestsParams params =  modelMapper.map(req, AddToUserInterestsParams.class);
        var wishList = userInterestsService.addUserInterest(params);
        if(wishList.isSuccess()){
            var res = modelMapper.map(wishList.getSuccess(), UserInterestResponse.class);
            return new  ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiError(wishList.getFailure().getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-wishlist")
    public ResponseEntity<?> deleteWishList(@Valid  @RequestBody AddOrDeleteUserInterestRequest req, BindingResult rs){
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(new ApiError(errors), HttpStatus.BAD_REQUEST);
        }
        //delete
        DeleteUserInterestsParams deleteUserInterestsParams = modelMapper.map(req,DeleteUserInterestsParams.class);
        var isDeleted = userInterestsService.deleteUserInterest(deleteUserInterestsParams);
        if(isDeleted.isSuccess()){
            return new  ResponseEntity<>(isDeleted, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiError(isDeleted.getFailure().getMessage()), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/get-user-interest")
    public ResponseEntity<?> getUserInterestlist( @RequestBody UserInterestRequest req, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token == null){
            return null;
        }
        String newToken = token.substring(7);
        var email = iJwtService.extractUserName(newToken);
        User user = iAuthService.findUserByEmail(email);

        GetUserInterestByUserIdParams getUserInterestByUserIdParams = new GetUserInterestByUserIdParams();
        getUserInterestByUserIdParams.setUser_id(user.getId());
        getUserInterestByUserIdParams.setSize(req.getSize());
        getUserInterestByUserIdParams.setPage(req.getPage());
        getUserInterestByUserIdParams.setTitleSearch(req.getTitleSearch());
        var userInterestDTO = userInterestsService.getAlluserInterestByUserId(getUserInterestByUserIdParams);
        if(userInterestDTO.isSuccess()){
            return new  ResponseEntity<>(userInterestDTO.getSuccess(), HttpStatus.OK);
        }
        return new ResponseEntity<>(userInterestDTO.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }
}
