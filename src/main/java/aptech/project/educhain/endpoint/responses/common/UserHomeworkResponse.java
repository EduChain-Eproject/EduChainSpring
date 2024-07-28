package aptech.project.educhain.endpoint.responses.common;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.UserAnswerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHomeworkResponse {
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
