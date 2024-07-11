package aptech.project.educhain.domain.useCases.personalization.user_course.get_all_user_course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseParams {
    private int student_id;
    private int size;
    private int page;
    private String titleSearch;
}
