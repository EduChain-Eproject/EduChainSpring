package aptech.project.educhain.domain.useCases.blogs.BlogUseCases.CreateBlogUseCase;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateBlogUseCase {
    @Autowired
    BlogRepository blogRepository;

    public Blog execute(Blog newBlog) {
        try {
            return blogRepository.save(newBlog);
        }catch(Exception ex){
            ex.getMessage();
        }
        return null;
    }
}
