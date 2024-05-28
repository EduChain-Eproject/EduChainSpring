package aptech.project.educhain.repositories.blogs;

import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.blogs.Blog;
import aptech.project.educhain.models.blogs.UserBlogVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBlogVoteRepository extends JpaRepository<UserBlogVote, Integer> {
    public UserBlogVote findUserBlogVoteByUserAndAndBlog(User user, Blog blog);

    public int countByBlogAndVote(Blog blog, int vote);
}
