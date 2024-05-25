package aptech.project.educhain.controllers.auth;

import aptech.project.educhain.modelDTO.request.LoginRequest;
import aptech.project.educhain.modelDTO.response.JwtResponse;
import aptech.project.educhain.modelDTO.response.ResponseWithMessage;
import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.repositories.auth.AuthUserRepository;
import aptech.project.educhain.services.auth.IAuth.IJwtService;
import aptech.project.educhain.services.auth.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Auth")
public class AuthController {
    @Autowired
    private JwtService iJwtService;
    @Autowired
    private AuthUserRepository authUserRepository;
    @PostMapping("/login")
    public ResponseEntity<ResponseWithMessage> postLogin(@Valid @RequestBody LoginRequest loginRequest, BindingResult rs){
        if(rs.hasErrors()){
            StringBuilder errors = new StringBuilder();
            //ObjectError
            List<ObjectError> errorList = rs.getAllErrors();
            for(var err : errorList){
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(new ResponseWithMessage<>(null,errors.toString()));
        }
        else{
            loginRequest.getEmail();
            User user = authUserRepository.findUserByEmail(loginRequest.getEmail());
            if(user == null){
                return ResponseEntity.badRequest().body(new ResponseWithMessage<>(null,"cant find account"));
            }
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(iJwtService.generateToken(user));
            jwtResponse.setRefreshToken(iJwtService.generateRefreshToken(user.getId()));
            return ResponseEntity.ok(new ResponseWithMessage<>(jwtResponse,"Ok"));
        }
    }
}
