package aptech.project.educhain.domain.services.blogs;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.domain.dtos.blogs.BlogCommentDTO;

import java.util.List;

public interface IBlogCommentService {
    public BlogComment findComment(Integer id);

    public List<BlogComment> findCommentByBlog(Integer id);
    public BlogComment addComment(BlogComment comment);

    public BlogComment editComment(Integer id, BlogComment comment);

    public boolean deleteComment(Integer id);

    public BlogCommentDTO mapChildCommentService(BlogComment comment, Integer blogId);
}
