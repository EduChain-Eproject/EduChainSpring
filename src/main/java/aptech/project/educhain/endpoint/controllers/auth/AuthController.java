package aptech.project.educhain.endpoint.controllers.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.data.entities.accounts.EmailToken;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.domain.services.accounts.IEmailService;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.endpoint.requests.accounts.LoginRequest;
import aptech.project.educhain.endpoint.requests.accounts.RegisterRequest;
import aptech.project.educhain.endpoint.responses.JwtResponse;
import aptech.project.educhain.endpoint.responses.ResponseWithMessage;
import jakarta.validation.Valid;

@RestController
@RequestMapping("Auth")
public class AuthController {
    @Value("${base.url}")
    private String baseUrl;
    @Autowired
    private IEmailService iEmailService;
    @Autowired
    private IJwtService iJwtService;
    @Autowired
    private IAuthService iAuthService;

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
            loginRequest.getEmail();
            User user = iAuthService.findUserByEmail(loginRequest.getEmail());
            if (user == null) {
                return ResponseEntity.badRequest().body(new ResponseWithMessage<>(null, "cant find account"));
            }
            if (!user.getIsActive()) {
                return ResponseEntity.badRequest().body(new ResponseWithMessage<>(null,
                        "you has been block please contact our admin for more details"));
            }
            if (!user.getIsVerify()) {
                return ResponseEntity.badRequest().body(new ResponseWithMessage<>(null, "you email is not verify yet"));
            }
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(iJwtService.generateToken(user));
            jwtResponse.setRefreshToken(iJwtService.generateRefreshToken(user.getId()));
            return ResponseEntity.ok(new ResponseWithMessage<>(jwtResponse, "Ok"));
        }
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
        ;

        User checkUser = iAuthService.findUserByEmail(regis.getEmail());
        if (checkUser != null) {
            return ResponseEntity.badRequest().body("your email already exit please use other email");
            // send mails
        }
        User user = iAuthService.register(regis);
        EmailToken emailToken = iAuthService.createTokenEmail(user.getId());
        String url = baseUrl + emailToken.getVerifyToken();
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
}
