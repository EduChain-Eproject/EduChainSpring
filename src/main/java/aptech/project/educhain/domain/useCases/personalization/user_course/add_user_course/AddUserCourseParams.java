package aptech.project.educhain.domain.useCases.personalization.user_course.add_user_course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserCourseParams {
    private int student_id;
    private int course_id;
}
