package aptech.project.educhain.domain.dtos.courses;

import lombok.Data;

@Data
public class AnswerDTO {
    private Integer answerId;
    private Integer questionId;
    private String answerText;

    private QuestionDTO questionDto;
    private UserAnswerDTO userAnswerDtos;
}
