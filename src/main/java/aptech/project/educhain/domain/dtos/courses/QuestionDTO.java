package aptech.project.educhain.domain.dtos.courses;

import java.util.List;

import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String questionText;
    private Integer homeworkId;
    private Integer correctAnswerId;

    private HomeworkDTO homeworkDto;
    private List<AnswerDTO> answerDtos;
    private List<UserAnswerDTO> userAnswerDtos;
    private AnswerDTO correctAnswerDto;
}
