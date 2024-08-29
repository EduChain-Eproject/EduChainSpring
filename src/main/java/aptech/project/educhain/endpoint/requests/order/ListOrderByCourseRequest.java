package aptech.project.educhain.endpoint.requests.order;

import lombok.Data;

@Data
public class ListOrderByCourseRequest {
    private String titleSearch;
    private int page;
    private int size;
    private int courseId;
}
