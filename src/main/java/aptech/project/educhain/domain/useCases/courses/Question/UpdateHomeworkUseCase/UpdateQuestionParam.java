package aptech.project.educhain.domain.useCases.courses.Question.UpdateHomeworkUseCase;

import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Homework;
import lombok.Data;

import java.util.List;

@Data
public class UpdateQuestionParam {
    private String questionText;
    private Answers correctAnswer;
}
