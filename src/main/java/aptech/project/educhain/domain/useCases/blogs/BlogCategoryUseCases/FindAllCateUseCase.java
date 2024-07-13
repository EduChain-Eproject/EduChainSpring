package aptech.project.educhain.domain.useCases.blogs.BlogCategoryUseCases;

import aptech.project.educhain.data.entities.blogs.BlogCategory;
import aptech.project.educhain.data.repositories.blogs.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllCateUseCase {
    @Autowired
    BlogCategoryRepository repository;

    public List<BlogCategory> execute() {
        return repository.findAll();
    }
}
