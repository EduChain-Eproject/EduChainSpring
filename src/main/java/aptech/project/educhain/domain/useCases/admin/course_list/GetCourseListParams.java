package aptech.project.educhain.domain.useCases.admin.course_list;

import lombok.Data;

@Data
public class GetCourseListParams {
    private String titleSearch;
    private int page;
    private int size;
}
