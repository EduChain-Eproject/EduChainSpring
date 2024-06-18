package aptech.project.educhain.domain.services.blogs;

import aptech.project.educhain.data.repositories.blogs.BlogCommentRepository;
import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.serviceInterfaces.blogs.IBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
