package aptech.project.educhain.services.blogs.IBlogService.BlogSorting;

import aptech.project.educhain.models.blogs.Blog;

import java.util.List;

public class SortContext {
    private SortStrategy sortStrategy;

    public SortContext(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public List<Blog> executeSort(List<Blog> items){
        return sortStrategy.sort(items);
    }
}
