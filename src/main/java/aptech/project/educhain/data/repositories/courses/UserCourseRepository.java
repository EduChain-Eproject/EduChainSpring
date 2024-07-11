package aptech.project.educhain.data.repositories.courses;

import aptech.project.educhain.data.entities.chats.Chat;
import aptech.project.educhain.data.entities.courses.UserCourse;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, Integer>{
    @Query("SELECT uc FROM UserCourse uc " +
            "JOIN uc.course c " +
            "WHERE uc.user.id = :studentId " +
            "AND (:titleSearch IS NULL OR c.title LIKE %:titleSearch%)")
    List<UserCourse> findAllByStudentIdAndTitleSearch(@Param("studentId") int studentId,
                                                      @Param("titleSearch") String titleSearch,
                                                      Pageable pageable);
}
