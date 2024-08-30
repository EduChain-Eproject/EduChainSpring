package aptech.project.educhain.endpoint.requests.order;

import lombok.Data;

@Data
public class ListOrderByUserRequest {
    private String title;
    private int page;
    private int size;
    private int userId;
}
