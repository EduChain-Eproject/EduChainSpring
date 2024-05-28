package aptech.project.educhain.services.blogs;

import aptech.project.educhain.repositories.blogs.BlogRepository;
import aptech.project.educhain.services.blogs.IBlogService.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogService implements IBlogService {
    @Autowired
    BlogRepository blogRepository;
}
