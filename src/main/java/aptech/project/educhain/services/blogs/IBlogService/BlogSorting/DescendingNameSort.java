package aptech.project.educhain.services.blogs.IBlogService.BlogSorting;

import aptech.project.educhain.models.blogs.Blog;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DescendingNameSort implements SortStrategy{
    @Override
    public List<Blog> sort(List<Blog> items) {
        Collections.sort(items, new Comparator<Blog>() {
            @Override
            public int compare(Blog o1, Blog o2) {
                return o2.getTitle().compareTo(o1.getTitle());
            }
        });
        return items;
    }
}
