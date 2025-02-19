package aptech.project.educhain.domain.dtos.courses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

import aptech.project.educhain.data.entities.courses.AwardStatus;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;

@Data
public class AwardDTO {
    private Integer id;
    private AwardStatus status;
    private LocalDateTime submissionDate;
    private LocalDateTime reviewDate;
    private String comments;
    private String transactionHash;
    private BigDecimal tokenAmount;

    private Integer homeworkDtoId;

    private UserDTO userDto;
    private HomeworkDTO homeworkDto;
}
