package aptech.project.educhain.endpoint.responses.home;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.blogs.BlogCategoryDTO;
import aptech.project.educhain.domain.dtos.blogs.BlogCommentDTO;
import aptech.project.educhain.domain.dtos.blogs.UserBlogVoteDTO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
@Data
public class NewestBlogResponse {
    private Integer id;
    private Timestamp createdAt;
    private String title;
    private String blogText;
    private int voteUp;
    private String photo;
    private UserDTO user;
    private BlogCategoryDTO blogCategory;
    private List<BlogCommentDTO> blogComments;
    private List<UserBlogVoteDTO> userBlogVotes;
}
