package aptech.project.educhain.endpoint.responses.common;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import lombok.Data;

@Data
public class UserInterestResponse {
    private Integer id;
    private UserDTO userDto;
    private CourseDTO courseDto;
}
