package aptech.project.educhain.endpoint.requests.blogs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterBlogRequest {
    private String sortStrategy = "ascTitle";
    private String keyword;
    private Integer categoryId;
}
