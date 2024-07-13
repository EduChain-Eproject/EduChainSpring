package aptech.project.educhain.data.serviceImpl.blogs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.repositories.blogs.BlogCommentRepository;
import aptech.project.educhain.domain.services.blogs.IBlogCommentService;

@Service
public class BlogCommentService implements IBlogCommentService {
    @Autowired
    BlogCommentRepository blogCommentRepository;

    @Autowired
    BlogService blogService;

    @Override
    public BlogComment findComment(Integer id) {
        return blogCommentRepository.findById(id).get();
    }

    @Override
    public List<BlogComment> findAll() {
        return blogCommentRepository.findAll();
    }

    @Override
    public BlogComment create(BlogComment comment) {
        try {
            return blogCommentRepository.save(comment);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public BlogComment update(Integer id, BlogComment comment) {
        try {
            BlogComment com = findComment(id);
            if (com != null) {
                com.setText(comment.getText());
                return blogCommentRepository.save(com);
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        BlogComment comment = findComment(id);
        if (comment != null) {
            blogCommentRepository.delete(comment);
            return true;
        }
        return false;
    }

    public List<BlogComment> getByBlog(Integer id) {
        return blogCommentRepository.findBlogCommentByBlogAndParentCommentIsNull(blogService.findOneBlog(id));
    }
}
