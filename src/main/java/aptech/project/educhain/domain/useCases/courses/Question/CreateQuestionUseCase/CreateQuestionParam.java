package aptech.project.educhain.domain.useCases.courses.Question.CreateQuestionUseCase;

import aptech.project.educhain.data.entities.courses.Answers;
import lombok.Data;

import java.util.List;

@Data
public class CreateQuestionParam {
    private int homeworkId;
    private String questionText;
    private List<Answers> answers;
    private int correctAnswerId;
}
