package aptech.project.educhain.domain.dtos.blogs;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class BlogDTO {
    private Integer id;
    private Timestamp createdAt;
    private String title;
    private String blogText;
    private int voteUp;
    private int voteDown;
    private String photo;
    private UserDTO user;
    private BlogCategoryDTO blogCategory;
    private List<BlogCommentDTO> blogComments;
}
