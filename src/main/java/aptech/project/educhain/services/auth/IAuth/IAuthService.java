package aptech.project.educhain.services.auth.IAuth;

import aptech.project.educhain.request.accounts.RegisterRequest;
import aptech.project.educhain.models.accounts.EmailToken;
import aptech.project.educhain.models.accounts.User;


import java.util.Optional;

public interface IAuthService {
    User findUserById(int id);
    Optional<User> findUserDetailByEmal(String email);
    User findUserByEmail(String email);

    User register(RegisterRequest reg);
    EmailToken createTokenEmail(int id);
    EmailToken verifyEmailToken(String token);
    boolean verifyUser(int id);
}
