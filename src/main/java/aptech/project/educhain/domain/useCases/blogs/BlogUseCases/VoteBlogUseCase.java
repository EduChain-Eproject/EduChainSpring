package aptech.project.educhain.domain.useCases.blogs.BlogUseCases;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.UserBlogVote;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import aptech.project.educhain.domain.services.accounts.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoteBlogUseCase {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    AuthService authService;
    public Blog execute(Integer userId, Integer blogId, int vote){
        User user = authService.findUserById(userId);
        Blog blog = findBlog(blogId);
        UserBlogVote userBlogVote = voteRepository.findUserBlogVoteByUserAndAndBlog(user, blog);
        if (userBlogVote != null){
            if(vote == 0){
                voteRepository.delete(userBlogVote);
            } else {
                userBlogVote.setVote(vote);
                voteRepository.save(userBlogVote);
            }
        } else if (vote != 0) {
            userBlogVote = new UserBlogVote();
            userBlogVote.setUser(user);
            userBlogVote.setBlog(blog);
            userBlogVote.setVote(vote);
            voteRepository.save(userBlogVote);
        }

        blog.setVoteUp(voteRepository.countByBlogAndVote(blog, 1));
        blog.setVoteDown(voteRepository.countByBlogAndVote(blog, -1));
        return blogRepository.save(blog);
    }
}
