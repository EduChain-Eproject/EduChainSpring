package aptech.project.educhain.endpoint.responses.courses.question;

import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetListQuestionsByHomeworkResponse {
    private Integer id;
    private String questionText;
    private List<AnswerDTO> answers;
    private int correctAnswerID;
}
