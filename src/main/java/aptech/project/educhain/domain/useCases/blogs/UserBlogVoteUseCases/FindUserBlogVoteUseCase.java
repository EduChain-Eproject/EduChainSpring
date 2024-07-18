package aptech.project.educhain.domain.useCases.blogs.UserBlogVoteUseCases;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.UserBlogVote;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import aptech.project.educhain.data.repositories.blogs.UserBlogVoteRepository;
import aptech.project.educhain.data.serviceImpl.accounts.AuthService;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindOneBlogUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindUserBlogVoteUseCase {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserBlogVoteRepository voteRepository;

    @Autowired
    AuthService authService;

    @Autowired
    FindOneBlogUseCase findOneBlogUseCase;

    public UserBlogVote execute(Integer userId, Integer blogId) {
        User user = authService.findUserById(userId);
        Blog blog = findOneBlogUseCase.execute(blogId);
        return voteRepository.findUserBlogVoteByUserAndAndBlog(user, blog);
    }
}