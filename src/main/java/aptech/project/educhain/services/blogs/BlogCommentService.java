package aptech.project.educhain.services.blogs;

import aptech.project.educhain.models.blogs.Blog;
import aptech.project.educhain.models.blogs.BlogComment;
import aptech.project.educhain.repositories.blogs.BlogCommentRepository;
import aptech.project.educhain.repositories.blogs.BlogRepository;
import aptech.project.educhain.services.blogs.IBlogService.IBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public BlogComment create( BlogComment comment) {
        try {
            return blogCommentRepository.save(comment);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public BlogComment update(Integer id, BlogComment comment) {
        try {
            BlogComment com = findComment(id);
            if(com != null){
                com.setText(comment.getText());
                return blogCommentRepository.save(com);
            }
            return null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        BlogComment comment = findComment(id);
        if (comment != null){
            blogCommentRepository.delete(comment);
            return true;
        }
        return false;
    }

    public List<BlogComment> getByBlog(Integer id){
        return blogCommentRepository.findBlogCommentByBlogAndParentCommentIsNull(blogService.findBlog(id));
    }

    public Map<String, String> validateFields(String title, Integer userId, Integer blogCategoryId, String blogText) {
        Map<String, String> errors = new HashMap<>();
        if (title == null || title.isEmpty()) {
            errors.put("title", "Title is required");
        }
        if (userId == null) {
            errors.put("userId", "User ID is required");
        }
        if (blogCategoryId == null) {
            errors.put("blogCategoryId", "Blog Category ID is required");
        }
        if (blogText == null || blogText.isEmpty()) {
            errors.put("blogText", "Blog Text is required");
        }
        return errors;
    }
}
