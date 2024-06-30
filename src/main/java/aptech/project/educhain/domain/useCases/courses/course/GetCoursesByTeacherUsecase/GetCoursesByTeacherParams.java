package aptech.project.educhain.domain.useCases.courses.course.GetCoursesByTeacherUsecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCoursesByTeacherParams {
    private int teacherId;
    private String search;
    private int page;
    private int size;
    private String sortBy;
}
