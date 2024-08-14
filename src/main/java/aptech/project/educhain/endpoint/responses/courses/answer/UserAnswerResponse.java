package aptech.project.educhain.endpoint.responses.courses.answer;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import lombok.Data;

@Data
public class UserAnswerResponse {
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
