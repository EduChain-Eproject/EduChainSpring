package aptech.project.educhain.domain.dtos.courses;

import java.util.List;

import lombok.Data;

/**
 * CategoryDTO
 */
@Data
public class CategoryDTO {
    private Integer id;
    private String categoryDescription;
    private String categoryName;

    private List<CourseDTO> courseDtos;
}