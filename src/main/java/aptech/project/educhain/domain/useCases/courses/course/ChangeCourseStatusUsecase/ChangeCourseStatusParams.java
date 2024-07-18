package aptech.project.educhain.domain.useCases.courses.course.ChangeCourseStatusUsecase;

import aptech.project.educhain.data.entities.courses.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeCourseStatusParams {
    private Integer courseId;
    private CourseStatus status;
}