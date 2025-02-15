package aptech.project.educhain.domain.useCases.personalization.user_course.get_all_user_course;

import aptech.project.educhain.data.entities.courses.UserCourse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseParams {
    private int studentId;
    private int page;
    private int size;
    private String titleSearch;
    private UserCourse.CompletionStatus completionStatus;
}
