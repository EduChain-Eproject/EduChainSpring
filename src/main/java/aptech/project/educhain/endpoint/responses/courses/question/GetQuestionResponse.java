package aptech.project.educhain.endpoint.responses.courses.question;

import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetQuestionResponse {
    private Integer homeworkId;

    private String questionText;

    private List<AnswerDTO> answers;

    private Integer correctAnswerId;
}
