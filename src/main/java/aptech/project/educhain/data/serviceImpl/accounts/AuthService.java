package aptech.project.educhain.data.serviceImpl.accounts;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    public AuthUserRepository authUserRepository;

    @Autowired
    @Lazy
    private PasswordEncoder encoder;

    @Override
    public User findUserById(int id) {
        try {
            User user = authUserRepository.findById(id).get();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<User> findUserDetailByEmal(String email) {
        try {
            return authUserRepository.findByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            return authUserRepository.findUserByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public User register(RegisterRequest reg) {
        try {
            User user = new User();
            user.setEmail(reg.getEmail());
            var encode = encoder.encode(reg.getPassword());
            user.setPassword(encoder.encode(reg.getPassword()));
            user.setFirstName(reg.getFirstName());
            user.setLastName(reg.getLastName());
            user.setPhone(reg.getPhone());
            user.setAddress(reg.getAddress());
            user.setRole(reg.getRole());
            user.setIsActive(true);
            user.setIsVerify(false);
            authUserRepository.save(user);
            User newUser = authUserRepository.findUserWithId(user.getId());
            return newUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public EmailToken createTokenEmail(int id) {
        try {
            EmailToken emailToken = new EmailToken();
            String token = generateRandomString();
            var timeEnd = System.currentTimeMillis() + EXPIRATION_TIME;
            emailToken.setTimeExpire(new Timestamp(timeEnd));
            emailToken.setVerifyToken(token);
            // find user by id
            User user = authUserRepository.findUserWithId(id);
            emailToken.setUser(user);
            emailVerifyRepository.save(emailToken);
            return emailToken;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    // generate token
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

    // verify email Token
    public EmailToken verifyEmailToken(String token) {
        try {
            EmailToken emailToken = emailVerifyRepository.findEmaiTokenWithString(token);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expireTime = emailToken.getTimeExpire().toLocalDateTime();
            if (emailToken.getVerifyToken() == null || now.isAfter(expireTime.plusMinutes(15))
                    || emailToken.getUser().getId() == null) {
                return null;
            }
            return emailToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // service change isVerify
    public boolean verifyUser(int id) {
        User user = authUserRepository.findUserWithId(id);
        user.setIsVerify(true);
        authUserRepository.save(user);
        return true;
    }
}
