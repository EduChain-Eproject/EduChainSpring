package aptech.project.educhain.endpoint.requests.Homework;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateHomeworkRequest {
    @NotNull(message = "cant recognize lesson")
    private int lessonId;

    @NotEmpty(message = "Title required")
    @Length(min = 5, max = 100, message = "Title between 5 and 100 characters")
    private String title;

    @NotEmpty(message = "description required")
    @Length(min = 5, max = 300, message = "description between 5 and 300 characters")
    private String description;
}
