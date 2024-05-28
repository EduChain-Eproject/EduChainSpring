package aptech.project.educhain.services.auth.IAuth;

import aptech.project.educhain.modelDTO.response.JwtResponse;
import aptech.project.educhain.models.accounts.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface IJwtService {
    String generateToken(User user);

    String generateRefreshToken(int id);

    String extractUserName(String token);

    <T> T extractClaims(String token, Function<Claims, T> claimsTFunction);

    JwtResponse resetToken(String token);

    boolean isTokenExpired(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
