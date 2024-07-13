package aptech.project.educhain.domain.useCases.courses.course.SearchCoursesUseCase;

import java.util.List;

import aptech.project.educhain.data.entities.courses.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSearchParams {
    private String search;
    private int page = 0;
    private int size = 10;
    private String sortBy = "title";
    private List<Integer> categoryIds;

    private CourseStatus status;
}