package aptech.project.educhain.domain.services.blogs;

import aptech.project.educhain.data.entities.blogs.BlogCategory;
import aptech.project.educhain.data.repositories.blogs.BlogCategoryRepository;
import aptech.project.educhain.data.serviceInterfaces.blogs.IBlogCategoryService;
import aptech.project.educhain.domain.useCases.blogs.BlogCategoryUseCases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCategoryService implements IBlogCategoryService {
    @Autowired
    CreateCateUseCase createCateUseCase;

    @Autowired
    DeleteCateUseCase deleteCateUseCase;

    @Autowired
    FindAllCateUseCase findAllCateUseCase;

    @Autowired
    FindOneCateUseCase findOneCateUseCase;

    @Autowired
    isExistUseCase isExistUseCase;

    @Autowired
    UpdateCateUseCase updateCateUseCase;


    @Override
    public BlogCategory findBlogCategory(Integer id) {
        return findOneCateUseCase.execute(id);
    }

    @Override
    public List<BlogCategory> findAll() {
        return findAllCateUseCase.execute();
    }

    @Override
    public BlogCategory create(BlogCategory blogCategory) {
        return createCateUseCase.execute(blogCategory);
    }

    @Override
    public BlogCategory update(Integer id, BlogCategory blogCategory) {
        return updateCateUseCase.execute(id, blogCategory);
    }

    @Override
    public boolean delete(Integer id) {
        return deleteCateUseCase.execute(id);
    }

    @Override
    public boolean isExist(String name) {
        return isExistUseCase.execute(name);
    }
}
