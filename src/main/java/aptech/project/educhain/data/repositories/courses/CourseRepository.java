package aptech.project.educhain.data.repositories.courses;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.courses.CourseStatus;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("SELECT c FROM Course c WHERE c.teacher.id = :teacherId AND "
            + "(:search IS NULL OR c.title LIKE %:search%)")
    Page<Course> findByTeacherIdAndTitleContaining(
            @org.springframework.data.repository.query.Param("teacherId") int teacherId,
            @org.springframework.data.repository.query.Param("search") String search,
            Pageable pageable);

    @Query("SELECT c FROM Course c JOIN c.categories cat WHERE "
            + "(LOWER(c.title) LIKE LOWER(CONCAT('%', :search, '%')) OR "
            + "LOWER(c.description) LIKE LOWER(CONCAT('%', :search, '%'))) "
            + "AND cat.id IN :categoryIds AND c.status = :status")
    Page<Course> findByCategoryIdsAndSearch(
            @Param("categoryIds") List<Integer> categoryIds,
            @Param("search") String search,
            @Param("status") CourseStatus status,
            Pageable pageable);

    @Query("SELECT c FROM Course c WHERE "
            + "((:search IS NULL OR LOWER(c.title) LIKE %:search%) OR "
            + "(:search IS NULL OR LOWER(c.description) LIKE %:search%)) AND "
            + "(:status IS NULL OR c.status = :status)")
    Page<Course> findBySearch(@Param("search") String search, Pageable pageable,
            @Param("status") CourseStatus status);

    @Query("SELECT DISTINCT c FROM Course c JOIN c.categories cat WHERE (cat IN :categories AND c.id <> :courseId) AND"
            + "(:status IS NULL OR c.status = :status)"
    )
    List<Course> findDistinctByCategoriesInAndIdNot(@Param("categories") List<Category> categories,
            @Param("courseId") Integer courseId, @Param("status") CourseStatus status);

    @Query("SELECT c FROM Course c WHERE c.id = :courseId")
    Course findCourseWithId(@Param("courseId") int courseId);

    @Query("SELECT uc.course "
            + "FROM UserCourse uc "
            + "GROUP BY uc.course.id "
            + "ORDER BY COUNT(uc.user.id) DESC")
    Optional<Course> findMostPopularCourse();
}
