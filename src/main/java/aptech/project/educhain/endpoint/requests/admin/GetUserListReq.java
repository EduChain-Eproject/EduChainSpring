package aptech.project.educhain.endpoint.requests.admin;

import lombok.Data;

@Data
public class GetUserListReq {
    private String nameSearch;
    private int page;
    private int size;
}
