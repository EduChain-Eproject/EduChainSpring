package aptech.project.educhain.endpoint.requests.blogs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterBlogRequest {
    private String sortStrategy;
    private String keyword;
    private Integer[] categoryIdArray;
}