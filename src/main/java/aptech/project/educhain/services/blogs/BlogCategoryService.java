package aptech.project.educhain.services.blogs;

import aptech.project.educhain.models.blogs.BlogCategory;
import aptech.project.educhain.repositories.blogs.BlogCategoryRepository;
import aptech.project.educhain.repositories.blogs.BlogRepository;
import aptech.project.educhain.services.blogs.IBlogService.IBlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCategoryService implements IBlogCategoryService {
    @Autowired
    BlogCategoryRepository repository;

    @Override
    public BlogCategory findBlogCategory(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<BlogCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public BlogCategory create(BlogCategory blogCategory) {
        try {
            BlogCategory category = repository.save(blogCategory);
            return category;
        }catch(Exception ex){
            ex.getMessage();
        }
        return null;
    }

    public boolean isExist(String name){
        return repository.existsBlogCategoryByCategoryName(name);
    }

    @Override
    public BlogCategory update(Integer id, BlogCategory blogCategory) {
        try {
            BlogCategory category = findBlogCategory(id);
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

    @Override
    public boolean delete(Integer id) {
        BlogCategory category = findBlogCategory(id);
        if(category != null){
            repository.delete(category);
            return true;
        }
        return false;
    }
}
