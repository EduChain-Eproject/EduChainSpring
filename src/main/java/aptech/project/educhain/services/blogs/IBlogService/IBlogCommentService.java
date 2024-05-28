package aptech.project.educhain.services.blogs.IBlogService;

import aptech.project.educhain.models.blogs.BlogComment;

import java.util.List;

public interface IBlogCommentService {
    public BlogComment findComment(Integer id);

    public List<BlogComment> findAll();

    public BlogComment create(BlogComment comment);

    public BlogComment update(Integer id, BlogComment comment);

    public boolean delete(Integer id);
}
