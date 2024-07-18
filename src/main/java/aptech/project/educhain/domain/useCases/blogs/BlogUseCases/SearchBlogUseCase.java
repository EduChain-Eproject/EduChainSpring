package aptech.project.educhain.domain.useCases.blogs.BlogUseCases;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchBlogUseCase {
    @Autowired
    BlogRepository blogRepository;

    public List<Blog> execute(List<Blog> blogs, String keyword){
        return blogs.stream()
                .filter(blog -> blog.getTitle().contains(keyword.toLowerCase())
                        || blog.getBlogText().contains(keyword.toLowerCase())
                        || blog.getUser().getUsername().contains(keyword.toLowerCase())
                )
                .collect(Collectors.toList());
    }
}
