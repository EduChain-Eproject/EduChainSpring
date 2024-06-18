package aptech.project.educhain.domain.services.blogs;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.serviceInterfaces.blogs.IBlogService;
import aptech.project.educhain.data.serviceInterfaces.blogs.BlogSorting.SortStrategy;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.CreateBlogUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.DeleteBlogUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindAllBlogUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindBlogByCategoryUseCases;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindOneBlogUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.SearchBlogUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.SortingBlogUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.UpdateBlogUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.ValidateCreateUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.ValidateUpdateUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.VoteBlogUseCase;

@Service
public class BlogService implements IBlogService {
    @Autowired
    CreateBlogUseCase createBlogUseCase;

    @Autowired
    DeleteBlogUseCase deleteBlogUseCase;

    @Autowired
    FindAllBlogUseCase findAllBlogUseCase;

    @Autowired
    FindBlogByCategoryUseCases findBlogByCategoryUseCases;

    @Autowired
    FindOneBlogUseCase findOneBlogUseCase;

    @Autowired
    SearchBlogUseCase searchBlogUseCase;

    @Autowired
    SortingBlogUseCase sortingBlogUseCase;

    @Autowired
    UpdateBlogUseCase updateBlogUseCase;

    @Autowired
    ValidateCreateUseCase validateCreateUseCase;

    @Autowired
    ValidateUpdateUseCase validateUpdateUseCase;

    @Autowired
    VoteBlogUseCase voteBlogUseCase;

    @Override
    public Blog findOneBlog(Integer id) {
        return findOneBlogUseCase.execute(id);
    }

    @Override
    public List<Blog> findAll() {
        return findAllBlogUseCase.execute();
    }

    @Override
    public Blog create(Blog newBlog) {
        return createBlogUseCase.execute(newBlog);
    }

    @Override
    public Blog update(Integer id, Blog blog) {
        return updateBlogUseCase.execute(id, blog);
    }

    @Override
    public boolean delete(Integer id) {
        return deleteBlogUseCase.execute(id);
    }

    @Override
    public List<Blog> sorting(List<Blog> blogs, SortStrategy sortStrategy) {
        return sortingBlogUseCase.execute(blogs, sortStrategy);
    }

    @Override
    public List<Blog> search(List<Blog> blogs, String keyword) {
        return searchBlogUseCase.execute(blogs, keyword);
    }

    @Override
    public List<Blog> findByCategory(List<Blog> blogs, Integer id) {
        return findBlogByCategoryUseCases.execute(blogs, id);
    }

    @Override
    public Map<String, String> validateFields(String title, Integer userId, Integer blogCategoryId, String blogText) {
        return validateFieldsUpdate(title, blogCategoryId, blogText);
    }

    @Override
    public Map<String, String> validateFieldsUpdate(String title, Integer blogCategoryId, String blogText) {
        return validateUpdateUseCase.execute(title, blogCategoryId, blogText);
    }

    @Override
    public Blog vote(Integer userId, Integer blogId, int vote) {
        return voteBlogUseCase.execute(userId, blogId, vote);
    }

    public SortStrategy getSortStrategy(String sortStrategy) {
        return sortingBlogUseCase.getSortStrategy(sortStrategy);
    }
}
