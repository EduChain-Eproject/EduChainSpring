package aptech.project.educhain.endpoint.requests.blogs;

import aptech.project.educhain.services.blogs.IBlogService.BlogSorting.SortStrategy;
import lombok.Data;

@Data
public class FilterBlogRequest {
    private String sortStrategy = "ascTitle";
    private String keyword;
    private Integer categoryId;
}
