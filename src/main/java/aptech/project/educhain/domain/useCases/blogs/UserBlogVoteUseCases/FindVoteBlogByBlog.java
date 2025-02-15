package aptech.project.educhain.domain.useCases.blogs.UserBlogVoteUseCases;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.UserBlogVote;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import aptech.project.educhain.data.repositories.blogs.UserBlogVoteRepository;
import aptech.project.educhain.data.serviceImpl.accounts.AuthService;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindOneBlogUseCase.FindOneBlogUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindVoteBlogByBlog {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserBlogVoteRepository voteRepository;

    @Autowired
    AuthService authService;

    @Autowired
    FindOneBlogUseCase findOneBlogUseCase;

    public List<UserBlogVote> execute(Integer blogId) {
        Blog blog = findOneBlogUseCase.execute(blogId);
        return voteRepository.findUserBlogVoteByBlog(blog);
    }
}