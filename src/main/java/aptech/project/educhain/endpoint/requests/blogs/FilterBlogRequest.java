package aptech.project.educhain.endpoint.requests.blogs;

import lombok.Data;

@Data
public class FilterBlogRequest {
    private String sortStrategy = "ascTitle";
    private String keyword;
    private Integer categoryId;
}
