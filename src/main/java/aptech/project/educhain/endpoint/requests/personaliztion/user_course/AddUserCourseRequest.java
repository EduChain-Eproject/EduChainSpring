package aptech.project.educhain.endpoint.requests.personaliztion.user_course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserCourseRequest {
    private int student_id;
    private int course_id;
}
