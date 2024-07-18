package aptech.project.educhain.endpoint.requests.personaliztion.user_course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseRequest {
    private int student_id;
    private int page;
    private int size;
    private String titleSearch;
}
