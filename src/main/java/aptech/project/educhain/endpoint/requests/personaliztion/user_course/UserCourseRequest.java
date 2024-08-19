package aptech.project.educhain.endpoint.requests.personaliztion.user_course;

import aptech.project.educhain.data.entities.courses.UserCourse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseRequest {
    private int page = 0;
    private int size = 10;
    private String titleSearch;
    private UserCourse.CompletionStatus completionStatus;
}
