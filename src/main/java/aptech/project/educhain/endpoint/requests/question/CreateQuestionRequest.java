package aptech.project.educhain.endpoint.requests.question;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateQuestionRequest {
    private int homeworkId;

    @NotEmpty
    @Length(min = 5, max = 200, message = "Question between 5 and 200 characters")
    private String questionText;

    private List<String> answerTexts;

    private int correctAnswerIndex = -1;
}
