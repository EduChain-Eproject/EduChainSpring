package aptech.project.educhain.data.entities.blogs;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.accounts.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_blogs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blogCategory_id", referencedColumnName = "id")
    private BlogCategory blogCategory;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "blogText", columnDefinition = "TEXT")
    private String blogText;

    @Column(name = "photo")
    private String photo;

    @Column(name = "VoteUp", columnDefinition = "INT DEFAULT 0")
    private int voteUp;

    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BlogComment> blogComments;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    private List<UserBlogVote> userBlogVotes;


}
