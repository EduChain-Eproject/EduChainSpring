package aptech.project.educhain.domain.dtos.courses;

import java.util.List;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.Data;

@Data
public class HomeworkDTO {
    private Integer id;
    private Integer userID;
    private Integer lessonID;
    private String title;
    private String description;

    private UserDTO userDto;
    private LessonDTO lessonDto;
    private List<QuestionDTO> questionDtos;
    private List<UserAnswerDTO> userAnswerDtos;
    private List<UserHomeworkDTO> userHomeworkDtos;
    private List<AwardDTO> userAwardDtos;
}
