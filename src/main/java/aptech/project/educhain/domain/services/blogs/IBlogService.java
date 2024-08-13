package aptech.project.educhain.domain.services.blogs;

import java.util.List;
import java.util.Map;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.domain.dtos.blogs.BlogDTO;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.BlogFilterUseCase.BlogFilterParam;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindAllBlogUseCase.GetAllBlogParams;
import org.springframework.data.domain.Page;

public interface IBlogService {
    public Blog findOneBlog(Integer id);

    public AppResult<Page<BlogDTO>> findAll(GetAllBlogParams params);

    public Blog create(Blog newBlog);

    public Blog update(Integer id, Blog blog);

    public boolean delete(Integer id);

    public AppResult<Page<BlogDTO>> filter(BlogFilterParam params);
}
