package aptech.project.educhain.request.blogs;

import aptech.project.educhain.services.blogs.IBlogService.BlogSorting.SortStrategy;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
public class FilterBlogRequest {
    private String sortStrategy = "ascTitle";
    private String keyword;
    private Integer categoryId;
}
