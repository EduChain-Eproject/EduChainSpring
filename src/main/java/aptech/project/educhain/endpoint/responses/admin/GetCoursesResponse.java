package aptech.project.educhain.endpoint.responses.admin;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCoursesResponse {
    private Integer id;
    private String title;
    private String description;
    private String avatarPath;
    private Double price;
    private String status;
    private UserDTO teacherDto;
    private List<CategoryDTO> categoryDTOList;
    private List<UserCourseDTO> participatedUserDtos;
}
