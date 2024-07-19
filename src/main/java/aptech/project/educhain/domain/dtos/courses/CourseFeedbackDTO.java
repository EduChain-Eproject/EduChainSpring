package aptech.project.educhain.domain.dtos.courses;

import java.math.BigDecimal;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.Data;

@Data
public class CourseFeedbackDTO {
    private String message;
    private BigDecimal rating;

    private UserDTO userDto;
    private CourseDTO courseDto;
}
