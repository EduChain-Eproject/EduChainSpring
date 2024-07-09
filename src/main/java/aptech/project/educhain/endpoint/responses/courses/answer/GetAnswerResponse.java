package aptech.project.educhain.endpoint.responses.courses.answer;

import lombok.Data;

@Data
public class GetAnswerResponse {
    private Integer answerId;
    private Integer questionId;
    private String answerText;
}
