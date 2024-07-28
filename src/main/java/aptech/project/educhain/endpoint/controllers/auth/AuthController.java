package aptech.project.educhain.endpoint.controllers.auth;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.data.entities.accounts.EmailToken;
import aptech.project.educhain.data.entities.accounts.ResetPasswordToken;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.accounts.UserSession;
import aptech.project.educhain.data.repositories.accounts.UserSessionRepository;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.domain.services.accounts.IEmailService;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.endpoint.requests.accounts.LoginRequest;
import aptech.project.educhain.endpoint.requests.accounts.ReNewToken;
import aptech.project.educhain.endpoint.requests.accounts.RegisterRequest;
import aptech.project.educhain.endpoint.requests.accounts.ResetEmailRequest;
import aptech.project.educhain.endpoint.requests.accounts.ResetPasswordRequest;
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

    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@Valid @RequestBody LoginRequest loginRequest, BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            ApiError apiError = new ApiError(errors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        User user = iAuthService.findUserByEmail(loginRequest.getEmail());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            ApiError apiError = new ApiError("Wrong email or password");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        if (user.getEmail() == null) {
            ApiError apiError = new ApiError("Cannot find your account");
            return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
        }

        if (!user.getIsVerify()) {
            ApiError apiError = new ApiError("Your email is not verified yet");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        if (!iAuthService.checkLoginDevice(user.getId())) {
            ApiError apiError = new ApiError("Your account is already logged in from another device");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        if (!user.getIsActive()) {
            ApiError apiError = new ApiError("You have been blocked. Please contact our admin for more details");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(iJwtService.generateToken(user));
        jwtResponse.setRefreshToken(iJwtService.generateRefreshToken(user.getId()));
        return ResponseEntity.ok(new ResponseWithMessage<>(jwtResponse, "Ok"));
    }

    // get user by email
    @GetMapping("/user-by-email/{email}")
    public ResponseEntity<ResponseWithMessage> getUserWithEmail(@PathVariable("email") String email) {
        User user = iAuthService.findUserByEmail(email);
        UserDTO userDtoResponse = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(new ResponseWithMessage<>(userDtoResponse, "success"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiError> logOut(HttpServletRequest request) {
        User user = iJwtService.getUserByHeaderToken(request.getHeader("Authorization"));

        boolean checkLogout = iAuthService.deleteUserSession(user.getId());

        if (!checkLogout) {
            return new ResponseEntity<>(new ApiError("Got error when logging out your account"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiError("Success logout"), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiError> postRegister(@Valid @RequestBody RegisterRequest regis, BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(new ApiError(errors), HttpStatus.BAD_REQUEST);
        }

        User checkUser = iAuthService.findUserByEmail(regis.getEmail());
        if (checkUser != null) {
            return new ResponseEntity<>(new ApiError("Your email already exists, please use another email."),
                    HttpStatus.BAD_REQUEST);
        }

        User user = iAuthService.register(regis);
        EmailToken emailToken = iAuthService.createTokenEmail(user.getId());
        String url = baseUrlVerify + emailToken.getVerifyToken();
        iEmailService.sendEmail(user.getEmail(), iEmailService.templateEmail(user.getEmail(), url));
        if (user.getEmail() == null) {
            return new ResponseEntity<>(new ApiError("Cannot register."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiError("Please check your email to verify."), HttpStatus.OK);
    }

    @PostMapping("/reset-access-token")
    public ResponseEntity<ApiError> resetAccessToken(@RequestBody ReNewToken token) {
        var email = iJwtService.extractUserNameWhenTokenExpire(token.getAccessToken());
        if (email == null) {
            return new ResponseEntity<>(new ApiError("Can't recognize email"), HttpStatus.BAD_REQUEST);
        }
        var isRefreshTokenValid = iJwtService.isRefreshTokenExpired(token.getRefreshToken());
        User user = iAuthService.findUserByEmail(email);
        if (isRefreshTokenValid) {
            UserSession userSession = userSessionRepository.findUserSessionWithId(user.getId());
            userSessionRepository.delete(userSession);
            return new ResponseEntity<>(new ApiError("Your token expired or invalid. Please re-login."),
                    HttpStatus.BAD_REQUEST);
        }
        String newToken = iJwtService.generateTokenAfterExpire(user);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(newToken);
        jwtResponse.setRefreshToken(token.getRefreshToken());
        return new ResponseEntity<>(new ApiError("Ok"), HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiError> verifyToken(@RequestParam("code") String token) {
        EmailToken checkToken = iAuthService.verifyEmailToken(token);
        if (checkToken == null) {
            return new ResponseEntity<>(new ApiError("Token not found or expired."), HttpStatus.NOT_FOUND);
        }
        boolean checkResult = iAuthService.verifyUser(checkToken.getUser().getId());
        if (!checkResult) {
            return new ResponseEntity<>(new ApiError("Server Error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiError("Success verify"), HttpStatus.OK);
    }

    @PostMapping("/send_mail")
    public ResponseEntity<ApiError> sendMail(@RequestBody ResetEmailRequest request) {
        User user = iAuthService.findUserByEmail(request.getEmail());
        if (user.getEmail() == null) {
            return new ResponseEntity<>(new ApiError("Can't find your email"), HttpStatus.NOT_FOUND);
        }
        ResetPasswordToken resetPasswordToken = iAuthService.createResetPasswordToken(user.getId());
        String baseurl = baseUrlResetPassword + resetPasswordToken.getResetPasswordToken();
        iEmailService.sendEmail(user.getEmail(), iEmailService.templateResetPassword(user.getEmail(), baseurl));
        return new ResponseEntity<>(new ApiError("Success sending email verify"), HttpStatus.OK);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<ApiError> resetAction(@Valid @RequestBody ResetPasswordRequest req, BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(new ApiError(errors), HttpStatus.BAD_REQUEST);
        }

        int checkResult = iAuthService.resetPasswordAction(req.getCode(), req.getPassword());
        switch (checkResult) {
            case 0:
                return new ResponseEntity<>(new ApiError("System can't find your token"), HttpStatus.BAD_REQUEST);
            case -1:
                return new ResponseEntity<>(new ApiError("Can't find your account"), HttpStatus.BAD_REQUEST);
            case -2:
                return new ResponseEntity<>(new ApiError("Your token is expired"), HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>(new ApiError("Success change password"), HttpStatus.OK);
        }
    }
}
