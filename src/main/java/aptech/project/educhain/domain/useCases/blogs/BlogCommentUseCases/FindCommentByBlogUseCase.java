package aptech.project.educhain.domain.useCases.blogs.BlogCommentUseCases;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.repositories.blogs.BlogCommentRepository;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindCommentByBlogUseCase {
    @Autowired
    BlogCommentRepository blogCommentRepository;

    @Autowired
    BlogRepository blogRepository;

    public List<BlogComment> execute(Integer id) {
        try {
            Blog blog = blogRepository.findById(id).get();
            return blogCommentRepository.findBlogCommentByBlogAndParentCommentIsNull(blog);
        }catch(Exception ex){
            ex.getMessage();
        }
        return null;
    }
}
