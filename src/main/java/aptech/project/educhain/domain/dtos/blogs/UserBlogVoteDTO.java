package aptech.project.educhain.domain.dtos.blogs;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class UserBlogVoteDTO {
    private Integer id;
    private Integer userId;
    private Integer blogId;
}
