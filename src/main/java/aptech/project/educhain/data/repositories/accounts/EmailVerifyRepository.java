package aptech.project.educhain.data.repositories.accounts;

import aptech.project.educhain.data.entities.accounts.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmailVerifyRepository extends JpaRepository<EmailToken,Integer> {
    @Query("SELECT u FROM EmailToken u WHERE u.verifyToken = :token")
    EmailToken findEmaiTokenWithString(String token);
}
