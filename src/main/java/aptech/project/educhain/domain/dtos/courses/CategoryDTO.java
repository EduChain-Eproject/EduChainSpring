package aptech.project.educhain.domain.dtos.courses;

import java.util.List;

import aptech.project.educhain.data.entities.courses.Course;
import jakarta.persistence.ManyToMany;

/**
 * CategoryDTO
 */
public class CategoryDTO {
    private String categoryName;

    private List<CourseDTO> courses;
}