package aptech.project.educhain.domain.useCases.courses.Question.CreateQuestionUseCase;

import java.util.List;

import lombok.Data;

@Data
public class CreateQuestionParam {
    private int homeworkId;
    private String questionText;
    private List<String> answerTexts;
    private int correctAnswerIndex;
}
