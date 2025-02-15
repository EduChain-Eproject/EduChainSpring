package aptech.project.educhain.endpoint.requests.answer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAnswerRequest {
    @NotEmpty(message = "answer Text required")
    private String answerText;
}
