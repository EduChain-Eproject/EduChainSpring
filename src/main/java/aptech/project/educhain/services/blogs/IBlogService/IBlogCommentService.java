package aptech.project.educhain.services.blogs.IBlogService;

import aptech.project.educhain.models.blogs.BlogComment;

import java.util.List;

public interface IBlogCommentService {
    public BlogComment findBlog(Integer id);

    public List<BlogComment> findAll();

    public BlogComment create(BlogComment comment);

    public BlogComment update(BlogComment comment);

    public BlogComment delete(Integer id);
}
