package aptech.project.educhain.endpoint.controllers.auth;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
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
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.domain.services.accounts.IEmailService;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.endpoint.requests.accounts.LoginRequest;
import aptech.project.educhain.endpoint.requests.accounts.RegisterRequest;
import aptech.project.educhain.endpoint.requests.accounts.ResetEmailRequest;
import aptech.project.educhain.endpoint.requests.accounts.ResetPasswordRequest;
import aptech.project.educhain.endpoint.responses.JwtResponse;
import aptech.project.educhain.endpoint.responses.ResponseWithMessage;
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
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<ResponseWithMessage> postLogin(@Valid @RequestBody LoginRequest loginRequest,
            BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            // ObjectError
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(new ResponseWithMessage<>(null, errors.toString()));
        } else {
            User user = iAuthService.findUserByEmail(loginRequest.getEmail());
            String encodedPassword = passwordEncoder.encode(loginRequest.getPassword());
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                return ResponseEntity.badRequest().body(new ResponseWithMessage<>(null, "wrong password"));
            }
            if (user.getEmail() == null) {
                return ResponseEntity.badRequest().body(new ResponseWithMessage<>(null, "cant find account"));
            }
            if (!user.getIsVerify()) {
                return ResponseEntity.badRequest().body(new ResponseWithMessage<>(null, "you email is not verify yet"));
            }
            if (!iAuthService.checkLoginDevice(user.getId())) {
                return ResponseEntity.badRequest().body(new ResponseWithMessage<>(null, "your account already login"));
            }
            if (!user.getIsActive()) {
                return ResponseEntity.badRequest().body(new ResponseWithMessage<>(null,
                        "you has been block please contact our admin for more details"));
            }
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(iJwtService.generateToken(user));
            jwtResponse.setRefreshToken(iJwtService.generateRefreshToken(user.getId()));
            return ResponseEntity.ok(new ResponseWithMessage<>(jwtResponse, "Ok"));
        }
    }

    // get user by email
    @GetMapping("user-by-email/{email}")
    public ResponseEntity<ResponseWithMessage> getUserWithEmail(@PathVariable("email") String email) {
        User user = iAuthService.findUserByEmail(email);
        UserDTO userDtoResponse = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(new ResponseWithMessage<>(userDtoResponse, "success"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(@RequestBody String email) {
        User user = iAuthService.findUserByEmail(email);
        boolean checkLogout = iAuthService.deleteUserSession(user.getId());
        if (!checkLogout) {
            return ResponseEntity.badRequest().body("Got error when logout your account");
        }
        return ResponseEntity.ok("success logout");
    }

    @PostMapping("/register")
    public ResponseEntity<String> postRegister(@Valid @RequestBody RegisterRequest regis, BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            // ObjectError
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
        User checkUser = iAuthService.findUserByEmail(regis.getEmail());
        if (checkUser != null) {
            return ResponseEntity.badRequest().body("your email already exit please use other email");
            // send mails a
        }

        User user = iAuthService.register(regis);
        EmailToken emailToken = iAuthService.createTokenEmail(user.getId());
        String url = baseUrlVerify + emailToken.getVerifyToken();
        iEmailService.sendEmail(user.getEmail(), iEmailService.templateEmail(user.getEmail(), url));
        if (user.getEmail() == null) {
            return ResponseEntity.badRequest().body("Cant Register");
        }
        return ResponseEntity.ok("please check your email to verify");
    }

    // catch token
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
    public ResponseEntity<String> resetAction(@Valid @RequestBody ResetPasswordRequest req, BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            // ObjectError
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
        // check resutl and give client message
        var checkResult = iAuthService.resetPasswordAction(req.getCode(), req.getPassword());
        if (checkResult == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("system cant find your token");
        }
        if (checkResult == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find your email");
        }
        if (checkResult == -2) {
            return ResponseEntity.badRequest().body("your token expire");
        }
        return ResponseEntity.ok("success change password");
    }
}
