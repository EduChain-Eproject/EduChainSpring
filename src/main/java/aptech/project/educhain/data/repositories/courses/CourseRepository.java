package aptech.project.educhain.data.repositories.courses;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import aptech.project.educhain.data.entities.courses.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c FROM Course c WHERE c.teacher.id = :teacherId AND " +
            "(:search IS NULL OR c.title LIKE %:search%)")
    Page<Course> findByTeacherIdAndTitleContaining(
            @org.springframework.data.repository.query.Param("teacherId") int teacherId,
            @org.springframework.data.repository.query.Param("search") String search,
            Pageable pageable);
}
