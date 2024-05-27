package aptech.project.educhain.services.blogs;

import aptech.project.educhain.models.blogs.Blog;
import aptech.project.educhain.models.blogs.BlogCategory;
import aptech.project.educhain.repositories.blogs.BlogRepository;
import aptech.project.educhain.services.blogs.IBlogService.BlogSorting.*;
import aptech.project.educhain.services.blogs.IBlogService.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService implements IBlogService {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogCategoryService categoryService;

    public Blog findBlog(Integer id){
        return blogRepository.findById(id).get();
    }

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Blog create(Blog newBlog) {
        try {
            return blogRepository.save(newBlog);
        }catch(Exception ex){
            ex.getMessage();
        }
        return null;
    }

    @Override
    public Blog update(Integer id, Blog eBlog) {
        try {
            Blog blog = findBlog(id);
            if(blog != null){
                blog.setId(id);
                blog.setTitle(eBlog.getTitle());
                blog.setBlogText(eBlog.getBlogText());
                blog.setBlogCategory(eBlog.getBlogCategory());
                blogRepository.saveAndFlush(blog);
                return blog;
            }
            return null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        Blog blog = findBlog(id);
        if(blog != null){
            blogRepository.delete(blog);
            return true;
        }
        return false;
    }

    @Override
    public List<Blog> sorting(List<Blog> blogs, SortStrategy sortStrategy) {
        SortContext sortContext = new SortContext(sortStrategy);
        return sortContext.executeSort(blogs);
    }

    @Override
    public List<Blog> search(String keyword) {
        return blogRepository.findBlogByTitleContaining(keyword);
    }

    public SortStrategy getSortStrategy(String sortStrategy) {
        if (sortStrategy == null) {
            return new AscendingNameSort();
        }
        switch (sortStrategy){
            case "ascTitle":
                return new AscendingNameSort();
            case "descTitle":
                return new DescendingNameSort();

            case "ascTime":
                return new AscendingTimeSort();
            case "descTime":
                return new DescendingTimeSort();
            default:
                return new AscendingNameSort();
        }
    }
}

