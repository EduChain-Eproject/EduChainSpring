package aptech.project.educhain.endpoint.requests.admin;

import lombok.Data;

@Data
public class CourseListRequest {
    private String titleSearch;
    private int page;
    private int size;
}
