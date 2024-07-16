package aptech.project.educhain.endpoint.responses.courses.course.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCourseResponse {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private String status;
}