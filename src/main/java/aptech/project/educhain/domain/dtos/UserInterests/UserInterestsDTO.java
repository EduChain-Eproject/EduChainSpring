package aptech.project.educhain.domain.dtos.UserInterests;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInterestsDTO {
    private Integer id;
    private UserDTO userDto;
    private CourseDTO courseDto;
}
