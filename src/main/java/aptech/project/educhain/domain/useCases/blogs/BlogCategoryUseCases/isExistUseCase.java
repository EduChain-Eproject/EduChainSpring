package aptech.project.educhain.domain.useCases.blogs.BlogCategoryUseCases;

import aptech.project.educhain.data.repositories.blogs.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class isExistUseCase {
    @Autowired
    BlogCategoryRepository blogCategoryRepository;

    public boolean execute(String name){
        return blogCategoryRepository.existsBlogCategoryByCategoryName(name);
    }

}
