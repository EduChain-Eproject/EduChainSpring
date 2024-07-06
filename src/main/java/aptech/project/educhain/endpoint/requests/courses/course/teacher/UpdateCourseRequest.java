package aptech.project.educhain.endpoint.requests.courses.course.teacher;

import java.util.List;

import aptech.project.educhain.data.entities.courses.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseRequest {
    private List<Integer> categoryIds;
    private String title;
    private String description;
    private Double price;
    private CourseStatus status;
}