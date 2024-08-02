package aptech.project.educhain.endpoint.requests.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateQuestionRequest {
    @NotEmpty(message = "question Text required")
    private String questionText;
    @NotNull(message = "correct Answer required")
    private Integer correctAnswerId;
}
