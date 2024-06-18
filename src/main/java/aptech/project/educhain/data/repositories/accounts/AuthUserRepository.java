package aptech.project.educhain.data.repositories.accounts;

import aptech.project.educhain.data.entities.accounts.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<User,Integer> {
   @Query("SELECT u FROM User u WHERE u.id = :id")
   User findUserWithId(int id);
   Optional<User> findByEmail(String email);
   @Query("SELECT u FROM User u WHERE u.email = :email")
   User findUserByEmail(String email);

}
