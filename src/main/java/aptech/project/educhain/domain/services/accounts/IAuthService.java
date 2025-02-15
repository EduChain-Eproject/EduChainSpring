package aptech.project.educhain.domain.services.accounts;

import aptech.project.educhain.data.entities.accounts.EmailToken;
import aptech.project.educhain.data.entities.accounts.ResetPasswordToken;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.endpoint.requests.accounts.RegisterRequest;

import java.util.Optional;

public interface IAuthService {
    User findUserById(int id);
    Optional<User> findUserDetailByEmal(String email);
    User findUserByEmail(String email);

    User register(RegisterRequest reg);
    EmailToken createTokenEmail(int id);
    EmailToken verifyEmailToken(Integer code,String email);
    boolean verifyUser(int id);
    ResetPasswordToken createResetPasswordToken(int id);
    int resetPasswordAction(Integer token,String password,String email);

//    boolean checkLoginDevice(int userId);
//    boolean deleteUserSession(int userId);


}
