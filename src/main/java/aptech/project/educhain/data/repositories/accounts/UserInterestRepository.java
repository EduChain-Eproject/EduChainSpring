package aptech.project.educhain.data.repositories.accounts;

import aptech.project.educhain.data.entities.accounts.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserInterestRepository extends JpaRepository<UserInterest,Integer> {
    @Query("SELECT ui FROM UserInterest ui WHERE ui.user.id = :userId")
    List<UserInterest> findByUserId(@Param("userId") int userId);

    @Query("SELECT ui FROM UserInterest ui WHERE ui.course.id = :courseId AND ui.user.id = :userId")
    UserInterest findByCourseIdAndUserId(@Param("courseId") int courseId, @Param("userId") int userId);
}
