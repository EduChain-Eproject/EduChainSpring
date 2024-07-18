package aptech.project.educhain.endpoint.requests.Homework;

import lombok.Data;

@Data
public class AnswerAQuestionReq {
    private Integer questionId;
    private Integer answerId;
}
