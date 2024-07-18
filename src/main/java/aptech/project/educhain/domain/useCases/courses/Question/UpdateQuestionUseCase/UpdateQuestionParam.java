package aptech.project.educhain.domain.useCases.courses.Question.UpdateQuestionUseCase;

import lombok.Data;

@Data
public class UpdateQuestionParam {
    private Integer id;
    private String questionText;
    private int correctAnswerId;
}
