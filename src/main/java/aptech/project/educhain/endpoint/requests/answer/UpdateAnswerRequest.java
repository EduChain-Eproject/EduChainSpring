package aptech.project.educhain.endpoint.requests.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateAnswerRequest {
    private String answerText;
}
