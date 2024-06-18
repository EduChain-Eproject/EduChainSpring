package aptech.project.educhain.domain.useCases.blogs.BlogUseCases;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import aptech.project.educhain.data.serviceInterfaces.blogs.BlogSorting.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SortingBlogUseCase {
    @Autowired
    BlogRepository blogRepository;

    public List<Blog> execute(List<Blog> blogs, aptech.project.educhain.services.blogs.IBlogService.BlogSorting.SortStrategy sortStrategy) {
        SortContext sortContext = new SortContext(sortStrategy);
        return sortContext.executeSort(blogs);
    }
    public SortStrategy getSortStrategy(String sortStrategy) {
        if (sortStrategy == null) {
            return new DescendingTimeSort();
        }
        switch (sortStrategy){
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
