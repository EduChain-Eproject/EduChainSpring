package aptech.project.educhain.endpoint.controllers.common;

import aptech.project.educhain.data.serviceImpl.wishlist.UserInterestsServiceImpl;
import aptech.project.educhain.domain.useCases.account.wishlist.add_to_user_interests.AddToUserInterestsParams;
import aptech.project.educhain.endpoint.requests.accounts.wish_list.WishListRequest;
import aptech.project.educhain.endpoint.responses.wishlist_response.WishListResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("STUDENT")
public class UserInterestsController {
    @Autowired
    public UserInterestsServiceImpl wishListService;
    @Autowired
    public ModelMapper modelMapper;
    @PostMapping("/add-to-wishlist")
    public ResponseEntity<?> addToWishList(@Valid  @RequestBody WishListRequest req, BindingResult rs){
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            // ObjectError
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
        //add
       AddToUserInterestsParams params =  modelMapper.map(req, AddToUserInterestsParams.class);
        var wishList = wishListService.addWishList(params);
        if(wishList.isSuccess()){
            var res = modelMapper.map(wishList.getSuccess(), WishListResponse.class);
            return new  ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(wishList.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @PostMapping("/delete-wishlist")
//    public ResponseEntity<?> deleteWishList(@Valid  @RequestBody WishListRequest req, BindingResult rs){
//        if (rs.hasErrors()) {
//            StringBuilder errors = new StringBuilder();
//            // ObjectError
//            List<ObjectError> errorList = rs.getAllErrors();
//            for (var err : errorList) {
//                errors.append(err.getDefaultMessage()).append("\n");
//            }
//            return ResponseEntity.badRequest().body(errors.toString());
//        }
//        //delete
//
//    }
}
