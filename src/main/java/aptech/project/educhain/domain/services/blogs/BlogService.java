package aptech.project.educhain.domain.services.blogs;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.serviceInterfaces.blogs.BlogSorting.SortStrategy;
import aptech.project.educhain.data.serviceInterfaces.blogs.IBlogService;
import aptech.project.educhain.domain.useCases.blogs.BlogCommentUseCases.VoteBlogUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
}

