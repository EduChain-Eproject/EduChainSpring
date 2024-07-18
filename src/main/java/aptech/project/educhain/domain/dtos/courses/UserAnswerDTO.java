package aptech.project.educhain.domain.dtos.courses;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.Data;

@Data
public class UserAnswerDTO {
    private Integer id;
    private UserDTO userDto;
    private Boolean isCorrect;

    private UserHomeworkDTO userHomeworkDto;
    private QuestionDTO questionDto;
    private AnswerDTO answerDto;

}
