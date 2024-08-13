package aptech.project.educhain.endpoint.requests.blogs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FindAllBlogRequest {
    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
}
