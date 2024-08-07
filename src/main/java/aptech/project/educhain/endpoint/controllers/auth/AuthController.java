package aptech.project.educhain.endpoint.controllers.auth;

import java.util.HashMap;
import java.util.Map;

import aptech.project.educhain.endpoint.requests.accounts.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.data.entities.accounts.EmailToken;
import aptech.project.educhain.data.entities.accounts.ResetPasswordToken;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.domain.services.accounts.IEmailService;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.endpoint.responses.JwtResponse;
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

        if (user.getRole().name().equals("TEACHER") && !user.getIsActive()) {
            ApiError apiError = new ApiError("You are teacher , your account need to be accepted by admin");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        if (!user.getIsActive()) {
            ApiError apiError = new ApiError("You have been blocked. Please contact our admin for more details");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(iJwtService.generateToken(user));
        jwtResponse.setRefreshToken(iJwtService.generateRefreshToken(user.getId()));
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiError> logOut(HttpServletRequest request) {
        User user = iJwtService.getUserByHeaderToken(request.getHeader("Authorization"));
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
        String url = baseUrlVerify;
        iEmailService.sendEmail(user.getEmail(), iEmailService.templateEmail(user.getEmail(), emailToken.getCode()));
        if (user.getEmail() == null) {
            return new ResponseEntity<>(new ApiError("Cannot register."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiError("Please check your email to verify."), HttpStatus.OK);
    }

    @PostMapping("/reset-access-token")
    public ResponseEntity<?> resetAccessToken(@RequestBody ReNewToken token) {
        var email = iJwtService.extractUserNameWhenTokenExpire(token.getAccessToken());
        if (email == null) {
            return new ResponseEntity<>(new ApiError("Can't recognize email"), HttpStatus.BAD_REQUEST);
        }
        var isRefreshTokenValid = iJwtService.isRefreshTokenExpired(token.getRefreshToken());
        User user = iAuthService.findUserByEmail(email);
        if (isRefreshTokenValid) {
            return new ResponseEntity<>(new ApiError("Your token expired or invalid. Please re-login."),
                    HttpStatus.BAD_REQUEST);
        }
        String newToken = iJwtService.generateTokenAfterExpire(user);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(newToken);
        jwtResponse.setRefreshToken(token.getRefreshToken());
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("re-send-verify")
    public ResponseEntity<ApiError> reSendCodeVerify(@RequestBody ReSendPasswordVerify req) {
    if(req == null){
            return new ResponseEntity<>(new ApiError("please submit email"), HttpStatus.NOT_FOUND);
        }
        User user = iAuthService.findUserByEmail(req.getEmail());
        if(user == null){
            return new ResponseEntity<>(new ApiError("your account not register yet"), HttpStatus.NOT_FOUND);
        }
        EmailToken emailToken = iAuthService.createTokenEmail(user.getId());
        String url = baseUrlVerify;
        iEmailService.sendEmail(user.getEmail(), iEmailService.templateEmail(user.getEmail(), emailToken.getCode()));
        return new ResponseEntity<>(new ApiError("success re-send code"), HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiError> verifyToken(@RequestBody Integer code) {
        EmailToken checkToken = iAuthService.verifyEmailToken(code);
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
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return new ResponseEntity<>(new ApiError("your email required"), HttpStatus.NOT_FOUND);
        }
        User user = iAuthService.findUserByEmail(request.getEmail());
        if (user.getEmail() == null) {
            return new ResponseEntity<>(new ApiError("Can't find your email"), HttpStatus.NOT_FOUND);
        }
        if (!user.getIsVerify()){
            return new ResponseEntity<>(new ApiError("your account not verify yet"), HttpStatus.NOT_FOUND);
        }
        ResetPasswordToken resetPasswordToken = iAuthService.createResetPasswordToken(user.getId());
        iEmailService.sendEmail(user.getEmail(), iEmailService.templateResetPassword(user.getEmail(), resetPasswordToken.getResetPasswordToken()));
        return new ResponseEntity<>(new ApiError("Success sending email verify"), HttpStatus.OK);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<ApiError> resetAction(@Valid @RequestBody ResetPasswordRequest req, BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(new ApiError(errors), HttpStatus.BAD_REQUEST);
        }

        int checkResult = iAuthService.resetPasswordAction(req.getCode(), req.getPassword(),req.getEmail());
        switch (checkResult) {
            case 0:
                return new ResponseEntity<>(new ApiError("System can't find your token"), HttpStatus.BAD_REQUEST);
            case -1:
                return new ResponseEntity<>(new ApiError("your email maybe wrong"), HttpStatus.BAD_REQUEST);
            case -2:
                return new ResponseEntity<>(new ApiError("Your token is expired"), HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>(new ApiError("Success change password"), HttpStatus.OK);
        }
    }
}
