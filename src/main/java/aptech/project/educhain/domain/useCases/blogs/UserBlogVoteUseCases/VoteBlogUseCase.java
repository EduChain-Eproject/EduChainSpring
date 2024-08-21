package aptech.project.educhain.domain.useCases.blogs.UserBlogVoteUseCases;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.UserBlogVote;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import aptech.project.educhain.data.repositories.blogs.UserBlogVoteRepository;
import aptech.project.educhain.data.serviceImpl.accounts.AuthService;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindOneBlogUseCase.FindOneBlogUseCase;

@Component
public class VoteBlogUseCase {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserBlogVoteRepository voteRepository;

    @Autowired
    AuthService authService;

    @Autowired
    FindOneBlogUseCase findOneBlogUseCase;
    @Transactional
    public Blog execute(Integer userId, Integer blogId, int vote) {
        try{
            User user = authService.findUserById(userId);
            Blog blog = findOneBlogUseCase.execute(blogId);
            UserBlogVote userBlogVote = voteRepository.findUserBlogVoteByUserAndAndBlog(user, blog);

            if (userBlogVote != null) {
                if (vote == 0) {
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
            return blogRepository.save(blog);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}