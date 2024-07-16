package aptech.project.educhain.domain.useCases.blogs.BlogCategoryUseCases;

import aptech.project.educhain.data.entities.blogs.BlogCategory;
import aptech.project.educhain.data.repositories.blogs.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateCateUseCase {
    @Autowired
    BlogCategoryRepository repository;

    @Autowired
    FindOneCateUseCase findOneCateUseCase;

    public BlogCategory execute(Integer id, BlogCategory blogCategory) {
        try {
            BlogCategory category = findOneCateUseCase.execute(id);
            if(category != null){
                category.setId(id);
                category.setCategoryName(blogCategory.getCategoryName());
                repository.saveAndFlush(category);
                return category;
            }
            return null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
