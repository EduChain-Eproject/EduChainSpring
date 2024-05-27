package aptech.project.educhain.services.blogs.IBlogService.BlogSorting;

import aptech.project.educhain.models.blogs.Blog;

import java.util.List;

public interface SortStrategy {
    public List<Blog> sort(List<Blog> item);
}
