package aptech.project.educhain.endpoint.requests.blogs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FindAllBlogRequest {
    private int page ;
    private int size;
     private String sortBy = "createdAt";
}
