package aptech.project.educhain.dto.blogs;

import aptech.project.educhain.models.blogs.BlogComment;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

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
