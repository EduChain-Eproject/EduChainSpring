package aptech.project.educhain.endpoint.responses.blogs;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.blogs.BlogCategoryDTO;
import aptech.project.educhain.domain.dtos.blogs.BlogCommentDTO;
import aptech.project.educhain.domain.dtos.blogs.UserBlogVoteDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class GetAllBlogResponse {
    private Integer id;
    private Timestamp createdAt;
    private String title;
    private String blogText;
    private int voteUp;
    private String photo;
    private UserDTO user;
    private BlogCategoryDTO blogCategory;
    private List<UserBlogVoteDTO> userBlogVotes;
}
