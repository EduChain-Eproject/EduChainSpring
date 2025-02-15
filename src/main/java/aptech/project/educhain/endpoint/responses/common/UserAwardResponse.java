package aptech.project.educhain.endpoint.responses.common;

import aptech.project.educhain.data.entities.courses.AwardStatus;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardResponse {
    private Integer id;
    private AwardStatus status;
    private LocalDateTime submissionDate;
    private LocalDateTime reviewDate;
    private String comments;

    private Integer homeworkDtoId;

    private UserDTO userDto;
    private HomeworkDTO homeworkDto;
}
