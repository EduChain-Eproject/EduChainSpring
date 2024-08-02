package aptech.project.educhain.domain.dtos.courses;

import java.math.BigDecimal;
import java.sql.Timestamp;

import aptech.project.educhain.data.entities.courses.UserCourse;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCourseDTO {
    private Integer id;
    private UserDTO userDto;
    private CourseDTO courseDto;
    private Timestamp enrollmentDate;
    private UserCourse.CompletionStatus completionStatus;
    private BigDecimal progress;
}
