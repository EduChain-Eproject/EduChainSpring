package aptech.project.educhain.domain.useCases.blogs.BlogUseCases;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllBlogUseCase {
    @Autowired
    BlogRepository blogRepository;

    public List<Blog> execute() {
        return blogRepository.findAll();
    }
}
