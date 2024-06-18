package aptech.project.educhain.domain.dto.blogs;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class BlogCommentDTO {
    private Integer id;
    private Timestamp createdAt;
    private String text;
    private int voteUp;
    private int voteDown;
    private String photo;
    private Integer userId;
    private Integer blogId;
    private Integer parentCommentId;
    private List<BlogCommentDTO> replies;
}
