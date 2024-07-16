package aptech.project.educhain.domain.useCases.blogs.BlogUseCases;

import aptech.project.educhain.data.entities.blogs.Blog;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindBlogByCategoryUseCases {
    public List<Blog> execute(List<Blog> blogs, Integer id){
        return blogs.stream().filter(
                        blog -> blog
                                .getBlogCategory().getId().equals(id))
                .collect(Collectors.toList());
    }
}
