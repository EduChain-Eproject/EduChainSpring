package aptech.project.educhain.services.blogs.IBlogService;

import aptech.project.educhain.models.blogs.Blog;
import aptech.project.educhain.services.blogs.IBlogService.BlogSorting.SortStrategy;

import java.util.List;

public interface IBlogService {
    public Blog findBlog(Integer id);

    public List<Blog> findAll();

    public Blog create(Blog newBlog);

    public Blog update(Integer id, Blog blog);

    public boolean delete(Integer id);

    public List<Blog> sorting(List<Blog> blogs, SortStrategy sortStrategy);

    public List<Blog> search(String keyword);
}
