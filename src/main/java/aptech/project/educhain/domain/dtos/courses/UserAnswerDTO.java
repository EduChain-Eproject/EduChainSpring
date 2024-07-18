package aptech.project.educhain.domain.dtos.courses;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.Data;

@Data
public class UserAnswerDTO {
    private Integer id;
    private Boolean isCorrect;

    private Integer userHomeworkId;
    private Integer questionId;
    private Integer answerId;

    private UserDTO userDto;
    private UserHomeworkDTO userHomeworkDto;
    private QuestionDTO questionDto;
    private AnswerDTO answerDto;

}
