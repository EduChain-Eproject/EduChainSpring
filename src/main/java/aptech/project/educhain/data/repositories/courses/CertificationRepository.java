package aptech.project.educhain.data.repositories.courses;

import org.springframework.data.jpa.repository.JpaRepository;
import aptech.project.educhain.data.entities.courses.Certification;
import aptech.project.educhain.data.entities.courses.CertificationStatus;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CertificationRepository extends JpaRepository<Certification, Integer> {
        @Query("SELECT c FROM Certification c WHERE c.user.id = :userId AND c.status = :status")
        List<Certification> findByUserId(@Param("userId") Integer userId,
                        @Param("status") CertificationStatus status);

        @Query("SELECT c FROM Certification c WHERE c.user.id = :userId AND c.course.id = :courseId")
        Certification findByUserIdAndCourseId(
                        @Param("userId") Integer userId,
                        @Param("courseId") Integer courseId);
}
