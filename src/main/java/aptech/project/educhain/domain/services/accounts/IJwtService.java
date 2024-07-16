package aptech.project.educhain.domain.services.accounts;

import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.endpoint.responses.JwtResponse;
import io.jsonwebtoken.Claims;

public interface IJwtService {
    String generateToken(User user);

    String generateRefreshToken(int id);

    String extractUserName(String token);

    <T> T extractClaims(String token, Function<Claims, T> claimsTFunction);

    JwtResponse resetToken(String token);

    boolean isTokenExpired(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isRefreshTokenExpired(String token);

    User getUserByHeaderToken(String token);
}
