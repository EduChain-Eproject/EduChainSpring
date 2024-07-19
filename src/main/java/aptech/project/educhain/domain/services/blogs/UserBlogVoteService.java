package aptech.project.educhain.domain.services.blogs;

import aptech.project.educhain.data.entities.blogs.UserBlogVote;
import aptech.project.educhain.domain.useCases.blogs.UserBlogVoteUseCases.FindUserBlogVoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.domain.useCases.blogs.UserBlogVoteUseCases.VoteBlogUseCase;

@Service
public class UserBlogVoteService implements IUserBlogVoteService {
    @Autowired
    VoteBlogUseCase voteBlogUseCase;

    @Autowired
    FindUserBlogVoteUseCase findUserBlogVoteUseCase;

    @Override
    public Blog vote(Integer userId, Integer blogId, int vote) {
        return voteBlogUseCase.execute(userId, blogId, vote);
    }

    public UserBlogVote findUserBlogVote(Integer userId, Integer blogId) {
        return findUserBlogVoteUseCase.execute(userId, blogId);
    }
}
