package aptech.project.educhain.endpoint.controllers.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aptech.project.educhain.common.ValidationError;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.endpoint.requests.accounts.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.data.entities.accounts.EmailToken;
import aptech.project.educhain.data.entities.accounts.ResetPasswordToken;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.accounts.UserSession;
import aptech.project.educhain.data.repositories.accounts.UserSessionRepository;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.domain.services.accounts.IEmailService;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.endpoint.responses.JwtResponse;
import aptech.project.educhain.endpoint.responses.ResponseWithMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("Auth")
public class AuthController {
    @Value("${base.url.verify}")
    private String baseUrlVerify;
    @Value("${base.url.reset.password}")
    private String baseUrlResetPassword;
    @Autowired
    private IEmailService iEmailService;
    @Autowired
    private IJwtService iJwtService;
    @Autowired
    private IAuthService iAuthService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserSessionRepository userSessionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@Valid @RequestBody LoginRequest loginRequest, BindingResult rs) {
        if (rs.hasErrors()) {
            ValidationError validationError = new ValidationError();
            rs.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                validationError.addError(fieldName, errorMessage);
            });
            return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
        }else {
        User user = iAuthService.findUserByEmail(loginRequest.getEmail());
        if (user == null) {
            return new ResponseEntity<>(new Failure("Wrong email or password"), HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            return new ResponseEntity<>(new Failure("Wrong email or password"), HttpStatus.BAD_REQUEST);
        }

        if (user.getEmail() == null) {
            return new ResponseEntity<>(new Failure("Cannot find your account"), HttpStatus.NOT_FOUND);
        }

        if (!user.getIsVerify()) {
            return new ResponseEntity<>(new Failure("Your email is not verified yet"), HttpStatus.BAD_REQUEST);
        }

        if (!iAuthService.checkLoginDevice(user.getId())) {
            return new ResponseEntity<>(new Failure("Your account is already logged in from another device"), HttpStatus.BAD_REQUEST);
        }

        if (!user.getIsActive()) {
            return new ResponseEntity<>(new Failure("You have been blocked. Please contact our admin for more details"), HttpStatus.BAD_REQUEST);
        }

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(iJwtService.generateToken(user));
        jwtResponse.setRefreshToken(iJwtService.generateRefreshToken(user.getId()));
        return ResponseEntity.ok(new ResponseWithMessage<>(jwtResponse, "Ok"));
    }
}
    // get user by email
    @GetMapping("/user-by-email/{email}")
    public ResponseEntity<ResponseWithMessage> getUserWithEmail(@PathVariable("email") String email) {
        User user = iAuthService.findUserByEmail(email);
        UserDTO userDtoResponse = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(new ResponseWithMessage<>(userDtoResponse, "success"));
    }

//    @PostMapping("/logout")
//    public ResponseEntity<String> logOut(HttpServletRequest request) {
//        String token = request.getHeader("Authorization");
//        if (token == null) {
//            return null;
//        }
//        String newToken = token.substring(7);
//        var email = iJwtService.extractUserName(newToken);
//        User user = iAuthService.findUserByEmail(email);
//
//        boolean checkLogout = iAuthService.deleteUserSession(user.getId());
//        if (!checkLogout) {
//            return ResponseEntity.badRequest().body("Got error when logout your account");
//        }
//        return ResponseEntity.ok("success logout");
//    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(@RequestBody LogOutRequest request) {
        User user = iAuthService.findUserByEmail(request.getEmail());

        boolean checkLogout = iAuthService.deleteUserSession(user.getId());
        if (!checkLogout) {
            return ResponseEntity.badRequest().body("Got error when logout your account");
        }
        return ResponseEntity.ok("success logout");
    }

    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@Valid @RequestBody RegisterRequest regis, BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            rs.getFieldErrors().forEach(error ->
                    errorMessages.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(". ")
            );
            return new ResponseEntity<>(new Failure(errorMessages.toString()), HttpStatus.BAD_REQUEST);
        }

        User checkUser = iAuthService.findUserByEmail(regis.getEmail());
        if (checkUser != null) {
            return new ResponseEntity<>(new Failure("Your email already exists, please use another email."), HttpStatus.BAD_REQUEST);
        }

        User user = iAuthService.register(regis);
        EmailToken emailToken = iAuthService.createTokenEmail(user.getId());
        String url = baseUrlVerify + emailToken.getVerifyToken();
        iEmailService.sendEmail(user.getEmail(), iEmailService.templateEmail(user.getEmail(), url));
        if (user.getEmail() == null) {
            return new ResponseEntity<>(new Failure("Cannot register."), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("Please check your email to verify.");
    }

    // reset access token
    @PostMapping("/reset-access-token")
    public ResponseEntity<ResponseWithMessage> resetAccessToken(@RequestBody ReNewToken token) {
        var email = iJwtService.extractUserNameWhenTokenExpire(token.getAccessToken());
        if (email == null) {
            return ResponseEntity.badRequest().body(new ResponseWithMessage<>(null, "cant recognize email"));
        }
        var isRefreshTokenValid = iJwtService.isRefreshTokenExpired(token.getRefreshToken());
        User user = iAuthService.findUserByEmail(email);
        if (isRefreshTokenValid) {
            UserSession userSession = userSessionRepository.findUserSessionWithId(user.getId());
            userSessionRepository.delete(userSession);
            return ResponseEntity.badRequest()
                    .body(new ResponseWithMessage<>(null, "your token expire or invalid please re-login"));
        }
        String newToken = iJwtService.generateTokenAfterExpire(user);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(newToken);
        jwtResponse.setRefreshToken(token.getRefreshToken());
        return ResponseEntity.ok(new ResponseWithMessage<>(jwtResponse, "Ok"));
        //
        // return ResponseEntity.ok(new ResponseWithMessage<>(null, "Ok"));
    }

    // catch token verify
    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam("code") String token) {
        EmailToken checkToken = iAuthService.verifyEmailToken(token);
        if (checkToken == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found or expired.");
        }
        boolean checkResult = iAuthService.verifyUser(checkToken.getUser().getId());
        if (checkResult == false) {
            return ResponseEntity.badRequest().body("Server Error");
        }
        return ResponseEntity.ok("success verify");
    }

    // send mail reset-password
    @PostMapping("/send_mail")
    public ResponseEntity<String> sendMail(@RequestBody ResetEmailRequest request) {
        // check email exits
        User user = iAuthService.findUserByEmail(request.getEmail());
        if (user.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find your email");
        }
        // send mail+create mail
        ResetPasswordToken resetPasswordToken = iAuthService.createResetPasswordToken(user.getId());
        String baseurl = baseUrlResetPassword + resetPasswordToken.getResetPasswordToken();
        iEmailService.sendEmail(user.getEmail(), iEmailService.templateResetPassword(user.getEmail(), baseurl));
        return ResponseEntity.ok("success sending email verify");
    }

    // api for reset password
    @PostMapping("/reset_password")
    public ResponseEntity<?> resetAction(@Valid @RequestBody ResetPasswordRequest req, BindingResult rs) {
        ValidationError validationError = new ValidationError();

        if (rs.hasErrors()) {
            rs.getFieldErrors().forEach(error -> {
                validationError.addError(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(validationError.getErrors());
        }
        // check resutl and give client message
        var checkResult = iAuthService.resetPasswordAction(req.getCode(), req.getPassword());
        if (checkResult == 0) {
            return new ResponseEntity<>(new Failure("system cant find your token"),HttpStatus.BAD_REQUEST);
        }
        if (checkResult == -1) {
            return new ResponseEntity<>(new Failure("cant find your account"),HttpStatus.BAD_REQUEST);
        }
        if (checkResult == -2) {
            return new ResponseEntity<>(new Failure("your token is expire"),HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("success change password");
    }
}
