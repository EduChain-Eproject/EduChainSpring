package aptech.project.educhain.endpoint.requests.blogs;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogCategoryRequest {
    @NotEmpty
    private String categoryName;
}