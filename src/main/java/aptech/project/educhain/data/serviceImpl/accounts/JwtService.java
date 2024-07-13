package aptech.project.educhain.data.serviceImpl.accounts;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import aptech.project.educhain.data.entities.accounts.RefreshToken;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.repositories.accounts.JwtRepository;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.endpoint.responses.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtService implements IJwtService {
    @Autowired
    private JwtRepository jwtRepository;
    @Autowired
    private IAuthService iAuthService;

    private final SecretKey Key;
    private static final long ACCESS_EXPIRATION_TIME = 86400000;
    private static final long REFRESH_EXPIRATION_TIME = 86400000;

    // custom key for accessToken
    public JwtService() {
        String sercetStringKey = "92c574cd4d02112b43ecbd28ed3d63e72fc5b53d033f5096021d5cd9d3cbbb8192c574cd4d02112b43ecbd28ed3d63e72fc5b53d033f5096021d5cd9d3cbbb81";
        byte[] keyBytes = Base64.getDecoder().decode(sercetStringKey.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    private SecretKey getSigninKey() {
        return this.Key;
    }

    // create accessToken
    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    // create refreshToken
    public String generateRefreshToken(int id) {
        if (id == 0) {
            return null;
        }
        RefreshToken exitsToken = jwtRepository.findByUserId(id);
        if (exitsToken != null) {
            exitsToken.setRefreshToken(generateRandomString());
            long timeEnd = System.currentTimeMillis() + REFRESH_EXPIRATION_TIME;
            exitsToken.setExpireAt(new Timestamp(timeEnd));
            jwtRepository.save(exitsToken);
            return exitsToken.getRefreshToken();
        }
        var newRefreshToken = new RefreshToken();
        // create random string
        String refreshToken = generateRandomString();
        newRefreshToken.setRefreshToken(refreshToken);
        // create end time refresh token
        var timeEnd = System.currentTimeMillis() + REFRESH_EXPIRATION_TIME;
        newRefreshToken.setExpireAt(new Timestamp(timeEnd));
        User user = iAuthService.findUserById(id);
        newRefreshToken.setUser(user);
        jwtRepository.save(newRefreshToken);
        return newRefreshToken.getRefreshToken();
    }

    // create random string for refresh token
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

    // get Email
    public String extractUserName(String token) {
        try {
            return extractClaims(token, Claims::getSubject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // function for decode token
    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        try {
            return claimsTFunction.apply(Jwts
                    .parser()
                    .verifyWith(Key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // reset accessToken when it expire
    public JwtResponse resetToken(String token) {
        String email = extractUserName(token);
        User user = iAuthService.findUserByEmail(email);
        if (isTokenExpired(token) || user == null) {
            return null;
        } else {
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(generateRefreshToken(user.getId()));
            jwtResponse.setAccessToken(generateToken(user));
            return jwtResponse;
        }
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    // check valid accessToken for jwtconfig
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String usernameFromToken = extractUserName(token);
        String usernameFromUserDetails = userDetails.getUsername();
        if (!usernameFromToken.equals(usernameFromUserDetails)) {
            return false;
        }
        if (isTokenExpired(token)) {
            return false;
        }
        return true;
    }
}
