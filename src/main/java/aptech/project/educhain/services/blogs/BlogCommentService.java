package aptech.project.educhain.services.blogs;

import aptech.project.educhain.models.blogs.BlogComment;
import aptech.project.educhain.repositories.blogs.BlogCommentRepository;
import aptech.project.educhain.services.blogs.IBlogService.IBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCommentService implements IBlogCommentService {
    @Autowired
    BlogCommentRepository blogCommentRepository;

    @Override
    public BlogComment findBlog(Integer id) {
        return null;
    }

    @Override
    public List<BlogComment> findAll() {
        return List.of();
    }

    @Override
    public BlogComment create(BlogComment comment) {
        return null;
    }

    @Override
    public BlogComment update(BlogComment comment) {
        return null;
    }

    @Override
    public BlogComment delete(Integer id) {
        return null;
    }
}
