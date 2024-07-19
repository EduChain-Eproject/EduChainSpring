package aptech.project.educhain.domain.dtos.courses;

import java.time.LocalDateTime;

import aptech.project.educhain.data.entities.courses.AwardStatus;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.Data;

@Data
public class AwardDTO {
    private Integer id;
    private AwardStatus status;
    private LocalDateTime submissionDate;
    private LocalDateTime reviewDate;
    private String comments;

    private Integer homeworkDtoId;

    private UserDTO userDto;
    private HomeworkDTO homeworkDto;
}
