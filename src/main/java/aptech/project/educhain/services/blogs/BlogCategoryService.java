package aptech.project.educhain.services.blogs;

import aptech.project.educhain.repositories.blogs.BlogRepository;
import aptech.project.educhain.services.blogs.IBlogService.IBlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogCategoryService implements IBlogCategoryService {
    @Autowired
    BlogRepository blogRepository;
}
