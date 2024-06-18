package aptech.project.educhain.domain.useCases.blogs.BlogCategoryUseCases;

import aptech.project.educhain.data.entities.blogs.BlogCategory;
import aptech.project.educhain.data.repositories.blogs.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCateUseCase {
    @Autowired
    BlogCategoryRepository repository;

    public BlogCategory execute(BlogCategory blogCategory) {
        try {
            BlogCategory category = repository.save(blogCategory);
            return category;
        }catch(Exception ex){
            ex.getMessage();
        }
        return null;
    }
}
