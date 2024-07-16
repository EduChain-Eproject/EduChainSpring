package aptech.project.educhain.domain.services.blogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.domain.useCases.blogs.BlogCommentUseCases.VoteBlogUseCase;

@Service
public class UserBlogVoteService implements IUserBlogVoteService {
    @Autowired
    VoteBlogUseCase voteBlogUseCase;

    @Override
    public Blog vote(Integer userId, Integer blogId, int vote) {
        return voteBlogUseCase.execute(userId, blogId, vote);
    }
}
