package aptech.project.educhain.domain.useCases.blogs.BlogUseCases;

import aptech.project.educhain.data.entities.blogs.Blog;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindBlogByCategoryUseCases {
    public List<Blog> execute(List<Blog> blogs, Integer[] categoryIds) {
        return blogs.stream()
                .filter(blog -> Arrays.asList(categoryIds).contains(blog.getBlogCategory().getId()))
                .collect(Collectors.toList());
    }
}