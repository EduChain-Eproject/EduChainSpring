package aptech.project.educhain.dto.blogs;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BlogCategoryDTO {
    private Integer id;
    private Timestamp createdAt;
    private String name;

}
