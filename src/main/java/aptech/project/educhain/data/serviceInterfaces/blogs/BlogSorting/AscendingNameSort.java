package aptech.project.educhain.data.serviceInterfaces.blogs.BlogSorting;

import aptech.project.educhain.data.entities.blogs.Blog;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AscendingNameSort implements SortStrategy {
    @Override
    public List<Blog> sort(List<Blog> items) {
        Collections.sort(items, new Comparator<Blog>() {
            @Override
            public int compare(Blog o1, Blog o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        return items;
    }
}
