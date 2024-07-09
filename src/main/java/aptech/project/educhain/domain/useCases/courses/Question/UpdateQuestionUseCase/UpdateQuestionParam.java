package aptech.project.educhain.domain.useCases.courses.Question.UpdateQuestionUseCase;

import aptech.project.educhain.data.entities.courses.Answers;
import lombok.Data;

@Data
public class UpdateQuestionParam {
    private Integer id;
    private String questionText;
    private int correctAnswerId;
}
