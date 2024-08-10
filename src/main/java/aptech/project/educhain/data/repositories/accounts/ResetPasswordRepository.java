package aptech.project.educhain.data.repositories.accounts;

import aptech.project.educhain.data.entities.accounts.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResetPasswordRepository extends JpaRepository<ResetPasswordToken,Integer> {
    @Query("SELECT u FROM ResetPasswordToken u WHERE u.resetPasswordToken = :token")
    ResetPasswordToken findResetToken(@Param("token") Integer token);
}
