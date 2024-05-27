package aptech.project.educhain.dto.blogs;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BlogDTO {
    private Long id;
    private Timestamp createdAt;
    private String title;
    private String blogText;
    private int voteUp;
    private int voteDown;
    private String photo;
    private Long userId;
    private Long blogCategoryId;
}
