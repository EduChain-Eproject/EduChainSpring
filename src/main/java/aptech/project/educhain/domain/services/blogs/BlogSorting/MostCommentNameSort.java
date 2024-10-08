package aptech.project.educhain.domain.services.blogs.BlogSorting;

import aptech.project.educhain.data.entities.blogs.Blog;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MostCommentNameSort implements SortStrategy {
    @Override
    public List<Blog> sort(List<Blog> items) {
        Collections.sort(items, new Comparator<Blog>() {
            @Override
            public int compare(Blog o1, Blog o2) {
                return Integer.compare(o2.getBlogComments().size(), o1.getBlogComments().size());
            }
        });
        return items;
    }
}
