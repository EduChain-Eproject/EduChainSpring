package aptech.project.educhain.services.blogs.IBlogService;

import aptech.project.educhain.models.blogs.BlogCategory;

import java.util.List;

public interface IBlogCategoryService {
    public BlogCategory findBlogCategory(Integer id);

    public List<BlogCategory> findAll();

    public BlogCategory create(BlogCategory blogCategory);

    public BlogCategory update(Integer id, BlogCategory blogCategory);

    public boolean delete(Integer id);
}