package aptech.project.educhain.data.serviceImpl.blogs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aptech.project.educhain.data.entities.blogs.BlogCategory;
import aptech.project.educhain.domain.services.blogs.IBlogCategoryService;
import aptech.project.educhain.domain.useCases.blogs.BlogCategoryUseCases.CreateCateUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogCategoryUseCases.DeleteCateUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogCategoryUseCases.FindAllCateUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogCategoryUseCases.FindOneCateUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogCategoryUseCases.UpdateCateUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogCategoryUseCases.isExistUseCase;

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
