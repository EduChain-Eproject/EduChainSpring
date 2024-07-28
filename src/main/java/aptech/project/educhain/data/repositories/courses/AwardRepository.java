package aptech.project.educhain.data.repositories.courses;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aptech.project.educhain.data.entities.courses.Award;

public interface AwardRepository extends JpaRepository<Award, Integer> {
    @Query("SELECT aw FROM Award aw WHERE aw.user.id = :userId AND aw.homework.id = :homeworkId")
    Optional<Award> findByUserIdAndHomeworkId(@Param("userId") Integer userId,
            @Param("homeworkId") Integer homeworkId);

    Page<Award> findByUserId(Integer userId, Pageable pageable);


    @Query("SELECT aw FROM Award aw WHERE aw.user.id = :userId AND aw.id = :awardId")
    Optional<Award> findByUserIdAndAwardId(@Param("userId") Integer userId, @Param("awardId") Integer awardId);
}
