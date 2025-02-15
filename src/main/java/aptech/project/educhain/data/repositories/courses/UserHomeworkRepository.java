package aptech.project.educhain.data.repositories.courses;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aptech.project.educhain.data.entities.courses.UserHomework;

public interface UserHomeworkRepository extends JpaRepository<UserHomework, Integer> {
    @Query("SELECT uh FROM UserHomework uh WHERE uh.user.id = :userId AND uh.homework.id = :homeworkId")
    Optional<UserHomework> findByUserIdAndHomeworkId(@Param("userId") Integer userId,
            @Param("homeworkId") Integer homeworkId);

    Page<UserHomework> findByUserId(Integer userId, Pageable pageable);
    @Query("SELECT uh FROM UserHomework uh WHERE uh.user.id = :userId AND " +
            "(:isSubmitted IS NULL OR uh.isSubmitted = :isSubmitted)")
    Page<UserHomework> findByUserIdAndIsSubmitted(@Param("userId") Integer userId,
                                                  @Param("isSubmitted") Boolean isSubmitted,
                                                  Pageable pageable);
}
