package aptech.project.educhain.data.repositories.blogs;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.UserBlogVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBlogVoteRepository extends JpaRepository<UserBlogVote, Integer> {
    public UserBlogVote findUserBlogVoteByUserAndAndBlog(User user, Blog blog);

    public int countByBlogAndVote(Blog blog, int vote);

    public List<UserBlogVote> findUserBlogVoteByBlog(Blog blog);

}
