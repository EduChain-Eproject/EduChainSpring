package aptech.project.educhain.domain.useCases.courses.UserHomework.AnswerQuestionUseCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerQuestionParams {
    private Integer userId;
    private Integer homeworkId;
    private Integer questionId;
    private Integer answerId;
}
