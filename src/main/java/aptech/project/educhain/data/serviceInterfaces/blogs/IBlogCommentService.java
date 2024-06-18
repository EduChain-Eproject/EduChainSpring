package aptech.project.educhain.data.serviceInterfaces.blogs;

import aptech.project.educhain.data.entities.blogs.BlogComment;

import java.util.List;

public interface IBlogCommentService {
    public BlogComment findComment(Integer id);

    public List<BlogComment> findAll();

    public BlogComment create(BlogComment comment);

    public BlogComment update(Integer id, BlogComment comment);

    public boolean delete(Integer id);
}
