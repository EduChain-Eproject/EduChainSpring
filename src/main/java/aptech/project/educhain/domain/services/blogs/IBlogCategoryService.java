package aptech.project.educhain.domain.services.blogs;

import aptech.project.educhain.data.entities.blogs.BlogCategory;

import java.util.List;

public interface IBlogCategoryService {
    public BlogCategory findBlogCategory(Integer id);

    public List<BlogCategory> findAll();

    public BlogCategory create(BlogCategory blogCategory);

    public BlogCategory update(Integer id, BlogCategory blogCategory);

    public boolean delete(Integer id);

    public boolean isExist(String name);
}