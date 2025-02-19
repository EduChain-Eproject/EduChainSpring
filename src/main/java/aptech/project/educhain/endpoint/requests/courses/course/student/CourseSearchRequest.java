package aptech.project.educhain.endpoint.requests.courses.course.student;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSearchRequest {
    private String search;
    private int page = 0;
    private int size = 6;
    private String sortBy = "title";
    private List<Integer> categoryIds;
}
