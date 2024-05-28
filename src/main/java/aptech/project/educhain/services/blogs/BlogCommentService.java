package aptech.project.educhain.services.blogs;

import aptech.project.educhain.repositories.blogs.BlogCommentRepository;
import aptech.project.educhain.services.blogs.IBlogService.IBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogCommentService implements IBlogCommentService {
    @Autowired
    BlogCommentRepository blogCommentRepository;
}
