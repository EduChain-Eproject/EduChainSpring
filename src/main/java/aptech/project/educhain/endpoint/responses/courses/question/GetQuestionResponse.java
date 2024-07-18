package aptech.project.educhain.endpoint.responses.courses.question;

import java.util.List;

import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import lombok.Data;

@Data
public class GetQuestionResponse {
    private Integer homeworkId;

    private String questionText;

    private List<AnswerDTO> answerDtos;

    private Integer correctAnswerId;
}
