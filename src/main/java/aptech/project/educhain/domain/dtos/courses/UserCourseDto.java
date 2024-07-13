package aptech.project.educhain.domain.dtos.courses;

import java.math.BigDecimal;
import java.util.Date;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.Data;

@Data
public class UserCourseDto {
    private UserDTO userDto;

    private CourseDTO courseDto;

    private Date enrollmentDate;

    private CompletionStatus completionStatus;

    private BigDecimal progress;

    public enum CompletionStatus {
        NOT_STARTED, IN_PROGRESS, COMPLETED
    }
}
