package aptech.project.educhain.data.serviceImpl.accounts;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import aptech.project.educhain.data.entities.accounts.ResetPasswordToken;
import aptech.project.educhain.data.entities.accounts.UserSession;
import aptech.project.educhain.data.repositories.accounts.ResetPasswordRepository;
import aptech.project.educhain.data.repositories.accounts.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import aptech.project.educhain.data.entities.accounts.EmailToken;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.accounts.EmailVerifyRepository;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.endpoint.requests.accounts.RegisterRequest;
import jakarta.transaction.Transactional;

@Service
public class AuthService implements IAuthService {

    private static final long EXPIRATION_TIME = 900000;
    @Autowired
    public EmailVerifyRepository emailVerifyRepository;
    @Autowired
    public ResetPasswordRepository resetPasswordRepository;
    @Autowired
    public AuthUserRepository authUserRepository;
    @Autowired
    public UserSessionRepository userSessionRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Value("${base.url.default.avatar}")
    private String defaultAvatar;


    @Override
    public User findUserById(int id){
        try{
            User user = authUserRepository.findById(id).get();
            return user;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Optional<User> findUserDetailByEmal(String email){
        try{
            return authUserRepository.findByEmail(email);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public User findUserByEmail(String email){
        try{
            return authUserRepository.findUserByEmail(email);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public User register(RegisterRequest reg) {
        try{
            User user = new User();
            user.setEmail(reg.getEmail());
            var encode = passwordEncoder.encode(reg.getPassword());
            user.setPassword(passwordEncoder.encode(reg.getPassword()));
            user.setFirstName(reg.getFirstName());
            user.setLastName(reg.getLastName());
            user.setPhone(reg.getPhone());
            user.setAddress(reg.getAddress());
            user.setRole(reg.getRole());
            user.setIsActive(true);
            user.setIsVerify(false);
            user.setAvatarPath(defaultAvatar);
            authUserRepository.save(user);
            User newUser = authUserRepository.findUserWithId(user.getId());
            return newUser;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    @Transactional
    public EmailToken createTokenEmail(int id){
        try {
            EmailToken emailToken = new EmailToken();
            String token = generateRandomString();
            var timeEnd = System.currentTimeMillis()+EXPIRATION_TIME;
            emailToken.setTimeExpire(new Timestamp(timeEnd));
            emailToken.setVerifyToken(token);
            //find user by id
            User user = authUserRepository.findUserWithId(id);
            emailToken.setUser(user);
            emailVerifyRepository.save(emailToken);
            return emailToken;
        }
        catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }

    //generate token
    public static String generateRandomString() {
        int length = 15;
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom RANDOM = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }

    //verify email Token
    public EmailToken verifyEmailToken(String token){
        try {
            EmailToken emailToken = emailVerifyRepository.findEmaiTokenWithString(token);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expireTime = emailToken.getTimeExpire().toLocalDateTime();
            if(emailToken.getVerifyToken() == null || now.isAfter(expireTime.plusMinutes(15)) || emailToken.getUser().getId() == null){
                return null;
            }
            return  emailToken;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //service change isVerify
    public boolean verifyUser(int id){
        User user = authUserRepository.findUserWithId(id);
        user.setIsVerify(true);
        authUserRepository.save(user);
        return true;
    }

    @Override
    public ResetPasswordToken createResetPasswordToken(int id) {
        try{
            ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
            String token = generateRandomString();
            var timeEnd = System.currentTimeMillis()+EXPIRATION_TIME;
            resetPasswordToken.setTimeExpire(new Timestamp(timeEnd));
            resetPasswordToken.setResetPasswordToken(token);
            User user = authUserRepository.findUserWithId(id);
            resetPasswordToken.setUser(user);
            resetPasswordRepository.save(resetPasswordToken);
            return resetPasswordToken;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public int resetPasswordAction(String token,String password){
        try{
            //find token
            ResetPasswordToken resetPasswordToken = resetPasswordRepository.findResetToken(token);
            if (resetPasswordToken.getResetPasswordToken() == null){
                return 0;
            }
            //find User with userId in token
            User user = authUserRepository.findUserWithId(resetPasswordToken.getUser().getId());
            if (user.getEmail() == null){
                return -1;
            }

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expireTime = resetPasswordToken.getTimeExpire().toLocalDateTime();
            if(now.isAfter(expireTime.plusMinutes(15))){
                return -2;
            }
            //change password
            String encodePassword = passwordEncoder.encode(password);
            user.setPassword(encodePassword);
            authUserRepository.save(user);
            return 1;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    //check login device
    public boolean checkLoginDevice(int userId){
        UserSession oldUserSession = userSessionRepository.findUserSessionWithId(userId);
        UserSession userSession = createUserSession(userId);
        if(oldUserSession == null){
            userSessionRepository.save(userSession);
            return true;
        }
        return false;
    }
    @Transactional
    public UserSession createUserSession(int userId){
        try {
            User user = authUserRepository.findUserWithId(userId);
            UserSession userSession = new UserSession();
            userSession.setUser(user);
            long timeEnd = System.currentTimeMillis();
            userSession.setLastLogin(new Timestamp(timeEnd));
            //find userSession by userid
            UserSession oldUserSession = userSessionRepository.findUserSessionWithId(userId);
            if(oldUserSession != null){
                return null;
            }
            userSessionRepository.save(userSession);
            String token = generateRandomString();
            userSession.setToken(token);
            return userSession;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //logout
    @Override
    @Transactional
    public boolean deleteUserSession(int userId){
        try {
            User user = authUserRepository.findUserWithId(userId);
            UserSession userSession = userSessionRepository.findUserSessionWithId(userId);
//            userSessionRepository.delete(userSession);
            userSessionRepository.deleteSessionByUserId(userSession.getUser().getId());
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
