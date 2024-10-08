package aptech.project.educhain.domain.useCases.blogs.BlogUseCases;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.repositories.blogs.BlogCommentRepository;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindOneBlogUseCase {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogCommentRepository blogCommentRepository;

    public Blog execute(Integer id){
        Blog blog = blogRepository.findById(id).get();
        List<BlogComment> comments = blogCommentRepository.findBlogCommentByBlogAndParentCommentIsNull(blog);
        blog.setBlogComments(comments);
        return blog;
    }
}
