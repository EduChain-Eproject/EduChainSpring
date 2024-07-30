package aptech.project.educhain.data.repositories.accounts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserSessionRepository extends JpaRepository<UserSession,Integer> {
    @Query("SELECT u FROM UserSession u WHERE u.user.id = :id")
    UserSession findUserSessionWithId(int id);


    @Modifying
    @Query("DELETE FROM UserSession us WHERE us.user.id = :userId")
    void deleteSessionByUserId(@Param("userId") int userId);
}
