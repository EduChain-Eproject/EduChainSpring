package aptech.project.educhain.domain.dtos.courses;

import java.math.BigDecimal;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.Data;

@Data
public class CourseFeedbackDTO {
    private UserDTO userDto;

    private CourseDTO courseDto;

    private String message;

    private BigDecimal rating;
}
