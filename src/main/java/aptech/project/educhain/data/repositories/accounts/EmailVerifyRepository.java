package aptech.project.educhain.data.repositories.accounts;

import aptech.project.educhain.data.entities.accounts.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmailVerifyRepository extends JpaRepository<EmailToken,Integer> {
    @Query("SELECT u FROM EmailToken u WHERE u.code = :code AND u.email = :email")
    EmailToken findEmailTokenByCodeAndEmail(@Param("code") Integer code, @Param("email") String email);

}
