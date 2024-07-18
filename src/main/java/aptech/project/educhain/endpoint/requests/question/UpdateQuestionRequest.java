package aptech.project.educhain.endpoint.requests.question;

import lombok.Data;

@Data
public class UpdateQuestionRequest {
    private String questionText;
    private Integer correctAnswerId;
}
