package aptech.project.educhain.endpoint.requests.blogs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterBlogRequest {
    private int page;
    private int size = 10;
    String keyword;
    String sortStrategy = "DATE_DESC";
    List<Integer> categoryIds = null;
}