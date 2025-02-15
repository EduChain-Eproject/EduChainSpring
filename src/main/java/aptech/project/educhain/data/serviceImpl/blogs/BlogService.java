package aptech.project.educhain.data.serviceImpl.blogs;

import java.util.List;
import java.util.Map;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.blogs.BlogDTO;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.BlogFilterUseCase.BlogFilterParam;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.BlogFilterUseCase.BlogFilterUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindAllBlogUseCase.GetAllBlogParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.domain.services.blogs.IBlogService;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.CreateBlogUseCase.CreateBlogUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.DeleteBlogUseCase.DeleteBlogUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindAllBlogUseCase.FindAllBlogUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindOneBlogUseCase.FindOneBlogUseCase;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.UpdateBlogUseCase.UpdateBlogUseCase;

@Service
public class BlogService implements IBlogService {
    @Autowired
    CreateBlogUseCase createBlogUseCase;

    @Autowired
    DeleteBlogUseCase deleteBlogUseCase;

    @Autowired
    FindAllBlogUseCase findAllBlogUseCase;

    @Autowired
    FindOneBlogUseCase findOneBlogUseCase;

    @Autowired
    UpdateBlogUseCase updateBlogUseCase;

    @Autowired
    BlogFilterUseCase blogFilterUseCase;

    @Override
    public Blog findOneBlog(Integer id) {
        return findOneBlogUseCase.execute(id);
    }

    @Override
    public AppResult<Page<BlogDTO>> findAll(GetAllBlogParams params) {
        return findAllBlogUseCase.execute(params);
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
    public AppResult<Page<BlogDTO>> filter(BlogFilterParam params) {
        return blogFilterUseCase.execute(params);
    }
}
