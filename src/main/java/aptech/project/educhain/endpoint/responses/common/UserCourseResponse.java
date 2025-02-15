package aptech.project.educhain.endpoint.responses.common;

import java.math.BigDecimal;

import aptech.project.educhain.data.entities.courses.UserCourse;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import lombok.Data;

@Data
public class UserCourseResponse {
    private Integer id;
    private UserDTO userDto;
    private CourseDTO courseDto;
    private UserCourse.CompletionStatus completionStatus;
    private BigDecimal progress;
}
