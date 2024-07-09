package aptech.project.educhain.endpoint.requests.question;

import aptech.project.educhain.data.entities.courses.Answers;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateQuestionRequest {
    private String questionText;
    private Integer correctAnswerId;
}
