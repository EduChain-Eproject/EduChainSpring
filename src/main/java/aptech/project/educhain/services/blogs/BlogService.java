package aptech.project.educhain.services.blogs;

import aptech.project.educhain.models.blogs.Blog;
import aptech.project.educhain.repositories.blogs.BlogRepository;
import aptech.project.educhain.services.blogs.IBlogService.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService implements IBlogService {
    @Autowired
    BlogRepository blogRepository;

    public Blog findBlog(Integer id){
        return blogRepository.getOne(id);
    }

    @Override
    public List<Blog> findAll() {
        return List.of();
    }

    @Override
    public Blog create(Blog newBlog) {
        return null;
    }

    @Override
    public Blog update(Blog blog) {
        return null;
    }

    @Override
    public Blog delete(Integer id) {
        return null;
    }
}

