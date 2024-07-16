package aptech.project.educhain.data.repositories.courses;

import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.CourseCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface CourseCategoryRepository extends JpaRepository<CourseCategory,Integer> {
    @Query("SELECT cc.category FROM CourseCategory cc WHERE cc.course.id = :courseId")
    List<Category> getCategoryByCourseId(@Param("courseId")int courseId);

    @Query("SELECT c FROM Category c JOIN c.courses co GROUP BY c ORDER BY COUNT(co) DESC")
    List<Category> findTopCategoriesWithMostCourses(Pageable pageable);
}

