package aptech.project.educhain.domain.useCases.admin.block_or_unblock;

import lombok.Data;

@Data
public class BlockOrUnBlockParams {
    private int userId;
    private Boolean blockValue;
}
