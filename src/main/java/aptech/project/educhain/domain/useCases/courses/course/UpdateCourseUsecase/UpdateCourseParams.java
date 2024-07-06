package aptech.project.educhain.domain.useCases.courses.course.UpdateCourseUsecase;

import java.util.List;

import aptech.project.educhain.data.entities.courses.CourseStatus;
import lombok.Data;

@Data
public class UpdateCourseParams {
    private int courseId;
    private List<Integer> categoryIds;
    private String title;
    private String description;
    private Double price;
    private CourseStatus status;
}