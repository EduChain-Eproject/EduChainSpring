package aptech.project.educhain.endpoint.responses.courses.question;

import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import lombok.Data;

import java.util.List;

@Data
public class CreateQuestionResponse {
    private Integer id;

    private Integer homeworkId;

    private String questionText;

    private List<AnswerDTO> answerDtos;

    private Integer correctAnswerId;
}
