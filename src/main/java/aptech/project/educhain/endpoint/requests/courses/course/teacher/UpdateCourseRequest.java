package aptech.project.educhain.endpoint.requests.courses.course.teacher;

import java.util.List;

import aptech.project.educhain.data.entities.courses.CourseStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseRequest {
    @NotEmpty(message = "category is required")
    private List<Integer> categoryIds;
    @NotEmpty(message = "title is required")
    private String title;
    @NotEmpty(message = "description is required")
    private String description;
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;
    private MultipartFile avatarCourse;
}