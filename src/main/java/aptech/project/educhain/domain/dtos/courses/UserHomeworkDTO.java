package aptech.project.educhain.domain.dtos.courses;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.Data;

@Data
public class UserHomeworkDTO {
    private Integer id;
    private Date submissionDate;
    private Double progress;
    private BigDecimal grade;
    private Boolean isSubmitted;

    private Integer homeworkDtoId;

    private HomeworkDTO homeworkDto;
    private UserDTO userDto;
    private List<UserAnswerDTO> userAnswerDtos;

    private AwardDTO userAwardDto;
}
