package aptech.project.educhain.endpoint.responses.courses.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAnswerResponse {
    private Integer answerId;
    private Integer questionId;
    private String answerText;
}