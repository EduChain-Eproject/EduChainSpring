package aptech.project.educhain.repositories.auth;

import aptech.project.educhain.models.accounts.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<RefreshToken,Integer> {
   RefreshToken findByUserId(int userId);

}
