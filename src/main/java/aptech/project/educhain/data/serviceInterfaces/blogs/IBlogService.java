package aptech.project.educhain.data.serviceInterfaces.blogs;

import java.util.List;
import java.util.Map;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.serviceInterfaces.blogs.BlogSorting.SortStrategy;

public interface IBlogService {
    public Blog findOneBlog(Integer id);

    public List<Blog> findAll();

    public Blog create(Blog newBlog);

    public Blog update(Integer id, Blog blog);

    public boolean delete(Integer id);

    public List<Blog> sorting(List<Blog> blogs, SortStrategy sortStrategy);

    public List<Blog> search(List<Blog> blogs, String keyword);

    public List<Blog> findByCategory(List<Blog> blogs, Integer id);

    public Map<String, String> validateFields(String title, Integer userId, Integer blogCategoryId, String blogText);

    public Map<String, String> validateFieldsUpdate(String title, Integer blogCategoryId, String blogText);

    public Blog vote(Integer userId, Integer blogId, int vote);
}
