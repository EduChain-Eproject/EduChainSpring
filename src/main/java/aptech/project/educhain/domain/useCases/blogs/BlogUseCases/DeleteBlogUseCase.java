package aptech.project.educhain.domain.useCases.blogs.BlogUseCases;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteBlogUseCase {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    FindOneBlogUseCase findOneBlogUseCase;

    public boolean execute(Integer id) {
        Blog blog = findOneBlogUseCase.execute(id);
        if(blog != null){
            blogRepository.delete(blog);
            return true;
        }
        return false;
    }
}
