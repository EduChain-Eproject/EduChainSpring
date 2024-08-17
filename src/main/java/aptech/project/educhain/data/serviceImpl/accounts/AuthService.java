package aptech.project.educhain.data.serviceImpl.accounts;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import aptech.project.educhain.data.entities.accounts.EmailToken;
import aptech.project.educhain.data.entities.accounts.ResetPasswordToken;
import aptech.project.educhain.data.entities.accounts.Role;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.accounts.EmailVerifyRepository;
import aptech.project.educhain.data.repositories.accounts.ResetPasswordRepository;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.endpoint.requests.accounts.RegisterRequest;
import jakarta.transaction.Transactional;

@Service
public class AuthService implements IAuthService {

    private static final long EXPIRATION_TIME = 20000;

    @Autowired
    public EmailVerifyRepository emailVerifyRepository;
    @Autowired
    public ResetPasswordRepository resetPasswordRepository;
    @Autowired
    public AuthUserRepository authUserRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Value("${base.url.default.avatar}")
    private String defaultAvatar;

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
            User user = authUserRepository.findUserByEmail(email);
            return user;
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
            user.setPassword(passwordEncoder.encode(reg.getPassword()));
            user.setFirstName(reg.getFirstName());
            user.setLastName(reg.getLastName());
            user.setPhone(reg.getPhone());
            user.setAddress(reg.getAddress());
            user.setIsVerify(false);
            user.setAvatarPath(defaultAvatar);
            if(Objects.equals(reg.getAccountType(), "TEACHER")){
                user.setIsActive(false);
                user.setRole(Role.TEACHER);
            }
            else {
                user.setIsActive(true);
                user.setRole(Role.STUDENT);
            }
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
            int combinedNumber = generateRandomNumber();
            EmailToken emailToken = new EmailToken();
            var timeEnd = System.currentTimeMillis() + EXPIRATION_TIME;
            emailToken.setTimeExpire(new Timestamp(timeEnd));
            emailToken.setCode(combinedNumber);
            // find user by id
            User user = authUserRepository.findUserWithId(id);
            emailToken.setEmail(user.getEmail());
            emailToken.setUser(user);
            emailVerifyRepository.save(emailToken);
            return emailToken;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    // generate token
    public static Integer generateRandomNumber() {
        Random random = new Random();

        int[] randomNumbers = new int[4];

        // Ensure the first digit is not zero
        randomNumbers[0] = random.nextInt(9) + 1;

        // Generate the remaining digits
        for (int i = 1; i < randomNumbers.length; i++) {
            randomNumbers[i] = random.nextInt(10);
        }

        StringBuilder combinedNumberStr = new StringBuilder();
        for (int number : randomNumbers) {
            combinedNumberStr.append(number);
        }

        return Integer.parseInt(combinedNumberStr.toString());
    }
    // verify email Token
    public EmailToken verifyEmailToken(Integer code,String email) {
        try {
            EmailToken emailToken = emailVerifyRepository.findEmailTokenByCodeAndEmail(code,email);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expireTime = emailToken.getTimeExpire().toLocalDateTime();
            if (emailToken.getCode() == null || now.isAfter(expireTime.plusMinutes(15))
                    || emailToken.getUser().getId() == null) {
                return null;
            }
            return emailToken;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // service change isVerify
    public boolean verifyUser(int id) {
        User user = authUserRepository.findUserWithId(id);
        user.setIsVerify(true);
        authUserRepository.save(user);
        return true;
    }

    @Override
    public ResetPasswordToken createResetPasswordToken(int id) {
        try {
            ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
            Integer token = generateRandomNumber();
            var timeEnd = System.currentTimeMillis() + EXPIRATION_TIME;
            resetPasswordToken.setTimeExpire(new Timestamp(timeEnd));
            resetPasswordToken.setResetPasswordToken(token);
            User user = authUserRepository.findUserWithId(id);
            resetPasswordToken.setUser(user);
            resetPasswordRepository.save(resetPasswordToken);
            return resetPasswordToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int resetPasswordAction(Integer token, String password,String email) {
        try {
            // find token
            ResetPasswordToken resetPasswordToken = resetPasswordRepository.findResetToken(token);
            //findemail
            User findUser = authUserRepository.findUserByEmail(email);
            if (resetPasswordToken.getResetPasswordToken() == null) {
                return 0;
            }
            // find User with userId in token
            User user = authUserRepository.findUserWithId(resetPasswordToken.getUser().getId());
            if (user.getEmail() == null || user.getId() != findUser.getId()) {
                return -1;
            }

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expireTime = resetPasswordToken.getTimeExpire().toLocalDateTime();
            if (now.isAfter(expireTime.plusMinutes(15))) {
                return -2;
            }
            // change password
            String encodePassword = passwordEncoder.encode(password);
            user.setPassword(encodePassword);
            authUserRepository.save(user);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    //function generate 4 number

    //function check number
}