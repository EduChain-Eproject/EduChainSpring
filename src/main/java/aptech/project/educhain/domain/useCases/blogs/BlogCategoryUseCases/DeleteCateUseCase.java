package aptech.project.educhain.domain.useCases.blogs.BlogCategoryUseCases;

import aptech.project.educhain.data.entities.blogs.BlogCategory;
import aptech.project.educhain.data.repositories.blogs.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteCateUseCase {
    @Autowired
    BlogCategoryRepository repository;

    @Autowired
    FindOneCateUseCase findOneCateUseCase;

    public boolean execute(Integer id) {
        BlogCategory category = findOneCateUseCase.execute(id);
        if(category != null){
            repository.delete(category);
            return true;
        }
        return false;
    }
}
