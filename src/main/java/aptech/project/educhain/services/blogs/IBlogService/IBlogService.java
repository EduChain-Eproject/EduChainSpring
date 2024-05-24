package aptech.project.educhain.services.blogs.IBlogService;

import aptech.project.educhain.models.blogs.Blog;

import java.util.List;

public interface IBlogService {
    public Blog findBlog(Integer id);

    public List<Blog> findAll();

    public Blog create(Blog newBlog);

    public Blog update(Blog blog);

    public Blog delete(Integer id);
}
