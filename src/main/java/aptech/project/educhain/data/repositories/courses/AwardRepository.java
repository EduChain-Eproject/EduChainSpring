package aptech.project.educhain.data.repositories.courses;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aptech.project.educhain.data.entities.courses.Award;

public interface AwardRepository extends JpaRepository<Award, Integer> {
    @Query("SELECT aw FROM Award aw WHERE aw.user.id = :userId AND aw.homework.id = :homeworkId")
    Optional<Award> findByUserIdAndHomeworkId(@Param("userId") Integer userId,
            @Param("homeworkId") Integer homeworkId);
}
