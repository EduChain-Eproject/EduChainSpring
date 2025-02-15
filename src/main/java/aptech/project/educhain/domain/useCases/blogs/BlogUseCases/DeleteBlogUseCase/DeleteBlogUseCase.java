package aptech.project.educhain.domain.useCases.blogs.BlogUseCases.DeleteBlogUseCase;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.UserBlogVote;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import aptech.project.educhain.data.repositories.blogs.UserBlogVoteRepository;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindOneBlogUseCase.FindOneBlogUseCase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteBlogUseCase {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    FindOneBlogUseCase findOneBlogUseCase;

    @Autowired
    UserBlogVoteRepository userBlogVoteRepository;

    @Transactional
    public boolean execute(Integer id) {
        Blog blog = findOneBlogUseCase.execute(id);
        if(blog != null){
            List<UserBlogVote> userBlogVote = userBlogVoteRepository.findUserBlogVoteByBlog(blog);
            if (userBlogVote != null) {
                userBlogVoteRepository.deleteAll(userBlogVote);
            }
            blogRepository.delete(blog);
            return true;
        }
        return false;
    }
}
