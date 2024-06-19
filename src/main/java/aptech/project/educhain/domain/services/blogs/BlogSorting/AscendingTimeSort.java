package aptech.project.educhain.domain.services.blogs.BlogSorting;

import aptech.project.educhain.data.entities.blogs.Blog;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AscendingTimeSort implements SortStrategy {
    @Override
    public List<Blog> sort(List<Blog> items) {
        Collections.sort(items, new Comparator<Blog>() {
            @Override
            public int compare(Blog o1, Blog o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });
        return items;
    }
}
