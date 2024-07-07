package aptech.project.educhain.endpoint.requests.courses.course.censor;

import aptech.project.educhain.data.entities.courses.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSearchRequest {
    private String search;
    private int page = 0;
    private int size = 10;
    private String sortBy = "title";

    private CourseStatus status = CourseStatus.APPROVED;
}
