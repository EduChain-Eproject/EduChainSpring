package aptech.project.educhain.domain.useCases.blogs.BlogUseCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import aptech.project.educhain.domain.services.blogs.BlogSorting.AscendingNameSort;
import aptech.project.educhain.domain.services.blogs.BlogSorting.AscendingTimeSort;
import aptech.project.educhain.domain.services.blogs.BlogSorting.DescendingNameSort;
import aptech.project.educhain.domain.services.blogs.BlogSorting.DescendingTimeSort;
import aptech.project.educhain.domain.services.blogs.BlogSorting.SortContext;
import aptech.project.educhain.domain.services.blogs.BlogSorting.SortStrategy;

@Component
public class SortingBlogUseCase {
    @Autowired
    BlogRepository blogRepository;

    public List<Blog> execute(List<Blog> blogs, SortStrategy sortStrategy) {
        SortContext sortContext = new SortContext(sortStrategy);
        return sortContext.executeSort(blogs);
    }

    public SortStrategy getSortStrategy(String sortStrategy) {
        if (sortStrategy == null) {
            return new DescendingTimeSort();
        }
        switch (sortStrategy) {
            case "ascTitle":
                return new AscendingNameSort();
            case "descTitle":
                return new DescendingNameSort();

            case "ascTime":
                return new AscendingTimeSort();
            case "descTime":
                return new DescendingTimeSort();
            default:
                return new DescendingTimeSort();
        }
    }
}
