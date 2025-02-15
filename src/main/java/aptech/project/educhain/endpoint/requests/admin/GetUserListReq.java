package aptech.project.educhain.endpoint.requests.admin;

import lombok.Data;

@Data
public class GetUserListReq {
    private String emailSearch;
    private int page;
    private int size;
}
