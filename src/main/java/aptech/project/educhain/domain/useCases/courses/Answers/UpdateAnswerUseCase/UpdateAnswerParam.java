package aptech.project.educhain.domain.useCases.courses.Answers.UpdateAnswerUseCase;

import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Homework;
import lombok.Data;

import java.util.List;

@Data
public class UpdateAnswerParam {
    private int id;
    private String answerText;
}
