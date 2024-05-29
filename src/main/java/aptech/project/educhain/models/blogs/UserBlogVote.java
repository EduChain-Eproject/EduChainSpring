package aptech.project.educhain.models.blogs;

import aptech.project.educhain.models.BaseModel;
import aptech.project.educhain.models.accounts.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tbl_user_blog_votes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBlogVote extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Column(name = "vote")
    private int vote; // 1 for upvote, -1 for downvote
}
