package aptech.project.educhain.data.repositories.courses;

import aptech.project.educhain.data.entities.chats.Chat;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.courses.UserCourse;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse, Integer>{
    @Query("SELECT uc FROM UserCourse uc " +
            "JOIN uc.course c " +
            "WHERE uc.user.id = :studentId " +
            "AND (:titleSearch IS NULL OR c.title LIKE %:titleSearch%)")
    Page<UserCourse> findAllByStudentIdAndTitleSearch(@Param("studentId") int studentId,
                                                      @Param("titleSearch") String titleSearch,
                                                      Pageable pageable);

    @Query("SELECT uc.course " +
            "FROM UserCourse uc " +
            "GROUP BY uc.course " +
            "ORDER BY COUNT(uc.user) DESC " +
            "LIMIT 4")
    List<Course> findMostPopularCourse();

    @Query("SELECT COUNT(DISTINCT uc.user.id) " +
            "FROM UserCourse uc " +
            "WHERE uc.course.id = :courseId")
    Long countDistinctStudentsByCourse(@Param("courseId") int courseId);

    @Query("SELECT COUNT(DISTINCT uc.user.id) FROM UserCourse uc")
    Long countDistinctStudents();

//    @Query("SELECT u.firstName || ' ' || u.lastName as teacherName, u.email as teacherEmail, COUNT(uc.id) as studentCount " +
//            "FROM UserCourse uc " +
//            "JOIN uc.course c " +
//            "JOIN c.teacher u " +
//            "GROUP BY u.id " +
//            "ORDER BY studentCount DESC " +
//            "LIMIT 1")
//    Object[] findTeacherWithMostStudents();
}
