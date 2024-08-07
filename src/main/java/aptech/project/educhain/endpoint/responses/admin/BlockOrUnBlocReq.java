package aptech.project.educhain.endpoint.responses.admin;

import lombok.Data;

@Data
public class BlockOrUnBlocReq {
    private int userId;
    private Boolean blockValue;
}
