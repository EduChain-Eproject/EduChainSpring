package aptech.project.educhain.endpoint.requests.Homework;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateHomeworkRequest {
    private int lessonId;

    @NotEmpty
    @Length(min = 5, max = 100, message = "Title between 5 and 100 characters")
    private String title;

    @NotEmpty
    @Length(min = 5, max = 300, message = "Title between 5 and 300 characters")
    private String description;
}
