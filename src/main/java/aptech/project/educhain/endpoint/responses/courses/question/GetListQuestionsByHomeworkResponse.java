package aptech.project.educhain.endpoint.responses.courses.question;

import java.util.List;

import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import lombok.Data;

@Data
public class GetListQuestionsByHomeworkResponse {
    private Integer id;
    private String questionText;
    private List<AnswerDTO> answers;
    private int correctAnswerID;
}
