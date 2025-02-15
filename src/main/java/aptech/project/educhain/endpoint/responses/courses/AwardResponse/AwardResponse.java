package aptech.project.educhain.endpoint.responses.courses.AwardResponse;

import aptech.project.educhain.data.entities.courses.AwardStatus;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AwardResponse {
    private Integer id;
    private AwardStatus status;
    private LocalDateTime submissionDate;
    private LocalDateTime reviewDate;
    private String comments;

    private Integer homeworkDtoId;

    private UserDTO userDto;
    private HomeworkDTO homeworkDto;
}
