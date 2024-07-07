package aptech.project.educhain.endpoint.responses.courses.course.censor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseByStatusResponse {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private String status;
}
