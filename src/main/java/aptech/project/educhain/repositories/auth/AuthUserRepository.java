package aptech.project.educhain.repositories.auth;

import aptech.project.educhain.models.accounts.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<User,Integer> {
   User findById(int id);
   Optional<User> findByEmail(String email);
   @Query("SELECT u FROM User u WHERE u.email = :email")
   User findUserByEmail(String email);

}
