package aptech.project.educhain.data.repositories.accounts;

import aptech.project.educhain.data.entities.accounts.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JwtRepository extends JpaRepository<RefreshToken,Integer> {
   @Query("SELECT rt FROM RefreshToken rt WHERE rt.user.id = :userId")
   RefreshToken findByUserId(int userId);

}
