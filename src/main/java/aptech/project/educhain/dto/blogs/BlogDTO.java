package aptech.project.educhain.dto.blogs;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BlogDTO {
    private Integer id;
    private Timestamp createdAt;
    private String title;
    private String blogText;
    private int voteUp;
    private int voteDown;
    private String photo;
    private Integer userId;
    private Integer blogCategoryId;
}
