package aptech.project.educhain.domain.useCases.blogs.BlogUseCases.UpdateBlogUseCase;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindOneBlogUseCase.FindOneBlogUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateBlogUseCase {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    FindOneBlogUseCase findOneBlogUseCase;

    public Blog execute(Integer id, Blog eBlog) {
        try {
            Blog blog = findOneBlogUseCase.execute(id);
            if(blog != null){
                blog.setId(id);
                blog.setTitle(eBlog.getTitle());
                blog.setBlogText(eBlog.getBlogText());
                blog.setBlogCategory(eBlog.getBlogCategory());
                blogRepository.saveAndFlush(blog);
                return blog;
            }
            return null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
