package aptech.project.educhain.domain.dtos.blogs;

import java.sql.Timestamp;
import java.util.List;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

@Data
public class BlogCommentDTO {
    private Integer id;
    private Timestamp createdAt;
    private String text;
    private int voteUp;
    private int voteDown;
    private String photo;
    private UserDTO user;
    private Integer blogId;
    private Integer parentCommentId;
    private List<BlogCommentDTO> replies;
}

