package aptech.project.educhain.domain.useCases.personalization.user_course.get_user_course;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserCourseParams {
    private Integer userId;
    private Integer courseId;
}
