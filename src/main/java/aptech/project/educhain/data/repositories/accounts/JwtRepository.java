package aptech.project.educhain.data.repositories.accounts;

import aptech.project.educhain.data.entities.accounts.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRepository extends JpaRepository<RefreshToken,Integer> {
   RefreshToken findByUserId(int userId);

}
