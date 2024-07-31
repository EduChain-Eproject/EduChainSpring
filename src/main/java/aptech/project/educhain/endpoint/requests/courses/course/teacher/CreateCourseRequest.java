package aptech.project.educhain.endpoint.requests.courses.course.teacher;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseRequest {
    @NotEmpty(message = "category is required")
    private List<Integer> categoryIds;

    @NotEmpty(message = "title is required")
    private String title;

    @NotEmpty(message = "description is required")
    private String description;
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;
}
