package aptech.project.educhain.controllers.blogs;

import aptech.project.educhain.services.blogs.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blog_comment")
public class BlogCommentController {
    @Autowired
    BlogCommentService blogCommentService;
}
