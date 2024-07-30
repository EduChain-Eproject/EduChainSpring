package aptech.project.educhain.endpoint.requests.personaliztion.user_course;

import aptech.project.educhain.data.entities.courses.UserCourse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseRequest {
    private int studentId;
    private int page;
    private int size;
    private String titleSearch;
    private UserCourse.CompletionStatus completionStatus;
}
