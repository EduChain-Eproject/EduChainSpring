package aptech.project.educhain.data.serviceInterfaces.blogs.BlogSorting;

import aptech.project.educhain.data.entities.blogs.Blog;

import java.util.List;

public interface SortStrategy {
    public List<Blog> sort(List<Blog> item);
}
