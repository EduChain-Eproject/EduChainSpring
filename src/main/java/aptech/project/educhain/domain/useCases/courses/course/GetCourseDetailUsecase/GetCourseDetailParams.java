package aptech.project.educhain.domain.useCases.courses.course.GetCourseDetailUsecase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCourseDetailParams {

    private Integer userId;
    private Integer courseId;
}
