package aptech.project.educhain.endpoint.controllers.personalization;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.services.personalization.UserProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("STUDENT")
public class UserCertificationController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    IJwtService iJwtService;

    @PostMapping("/my-certifications")
    public ResponseEntity<?> takeAllUserCourse(HttpServletRequest servletRequest) {
        var user = iJwtService.getUserByHeaderToken(servletRequest.getHeader("Authorization"));

        var result = userProfileService.getUserCertifications(user.getId());
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getSuccess(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()), HttpStatus.OK);
    }
}
