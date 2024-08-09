package aptech.project.educhain.data.repositories.accounts;

import aptech.project.educhain.data.entities.accounts.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<User,Integer> {
   @Query("SELECT u FROM User u WHERE u.id = :id")
   User findUserWithId(int id);
   @Query("SELECT u FROM User u WHERE u.email = :email")
   User findUserWithEmail(String email);
   Optional<User> findByEmail(String email);
   @Query("SELECT u FROM User u WHERE u.email = :email")
   User findUserByEmail(@Param("email") String email);

   @Query("SELECT u FROM User u WHERE u.email LIKE LOWER(CONCAT('%', :email, '%')) AND u.role <> 'ADMIN'")
   Page<User> findByNameContainingIgnoreCase(@Param("email") String email, Pageable pageable);



}
