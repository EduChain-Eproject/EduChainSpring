package aptech.project.educhain.endpoint.requests.courses.course.censor;

import aptech.project.educhain.data.entities.courses.CourseStatus;
import lombok.Data;

@Data
public class ApproveDeleteRequest {
    private CourseStatus status;
}