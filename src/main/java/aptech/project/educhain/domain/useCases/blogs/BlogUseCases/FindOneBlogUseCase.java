package aptech.project.educhain.domain.useCases.blogs.BlogUseCases;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindOneBlogUseCase {
    @Autowired
    BlogRepository blogRepository;

    public Blog execute(Integer id){
        return blogRepository.findById(id).get();
    }
}
