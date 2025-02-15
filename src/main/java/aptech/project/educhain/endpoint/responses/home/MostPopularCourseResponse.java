package aptech.project.educhain.endpoint.responses.home;

import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MostPopularCourseResponse {
        private Integer id;
        private String title;
        private String description;
        private Double price;
        private String status;
        private List<CategoryDTO> categoryDtos;
        private String avatarPath;
        private List<UserCourseDTO> participatedUserDtos;
}
