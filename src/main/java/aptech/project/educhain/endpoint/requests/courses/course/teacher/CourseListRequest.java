package aptech.project.educhain.endpoint.requests.courses.course.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseListRequest {
    private int teacherId;
    private String search;
    private int page = 0;
    private int size = 10;
    private String sortBy = "title";
}
