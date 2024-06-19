package aptech.project.educhain.domain.services.blogs;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.repositories.blogs.BlogCommentRepository;
import aptech.project.educhain.data.serviceInterfaces.blogs.IBlogCommentService;
import aptech.project.educhain.data.serviceInterfaces.blogs.IUserBlogVoteService;
import aptech.project.educhain.domain.useCases.blogs.BlogCommentUseCases.VoteBlogUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserBlogVoteService implements IUserBlogVoteService {
    @Autowired
    VoteBlogUseCase voteBlogUseCase;

    @Override
    public Blog vote(Integer userId, Integer blogId, int vote) {
        return voteBlogUseCase.execute(userId, blogId, vote);
    }
}
