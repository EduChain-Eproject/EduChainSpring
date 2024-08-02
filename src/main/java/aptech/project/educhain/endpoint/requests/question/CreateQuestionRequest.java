package aptech.project.educhain.endpoint.requests.question;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateQuestionRequest {
    private int homeworkId;

    @NotEmpty(message = "question Text required")
    @Length(min = 5, max = 200, message = "Question between 5 and 200 characters")
    private String questionText;

    @NotEmpty(message = "answer Text required")
    @NotNull(message = "answer Texts required ")
    private List<String> answerTexts;

    @NotNull(message = "correct Answer required")
    private int correctAnswerIndex = -1;
}
