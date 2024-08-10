package aptech.project.educhain.domain.useCases.courses.course.UpdateCourseUsecase;

import java.util.List;

import aptech.project.educhain.data.entities.courses.CourseStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateCourseParams {
    private int courseId;
    private List<Integer> categoryIds;
    private String title;
    private String description;
    private Double price;
    private CourseStatus status;
    private MultipartFile avatarCourse;
}