package aptech.project.educhain.endpoint.requests.courses.course.teacher;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseRequest {
    private List<Integer> categoryIds;

    @NotEmpty(message = "Email is required")
    private String title;

    @NotEmpty(message = "Email is required")
    private String description;

    private Double price;
}
