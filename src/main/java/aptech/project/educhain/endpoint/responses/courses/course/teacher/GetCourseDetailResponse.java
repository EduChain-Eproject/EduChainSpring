package aptech.project.educhain.endpoint.responses.courses.course.teacher;

import java.util.List;

import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import lombok.Data;

@Data
public class GetCourseDetailResponse {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private String status;
    private List<CategoryDTO> categoryDtos;
}
