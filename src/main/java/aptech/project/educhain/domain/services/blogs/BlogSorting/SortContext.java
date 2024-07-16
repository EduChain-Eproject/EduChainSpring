package aptech.project.educhain.domain.services.blogs.BlogSorting;

import aptech.project.educhain.data.entities.blogs.Blog;

import java.util.List;

public class SortContext {
    private SortStrategy sortStrategy;

    public SortContext(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public List<Blog> executeSort(List<Blog> items) {
        return sortStrategy.sort(items);
    }
}
