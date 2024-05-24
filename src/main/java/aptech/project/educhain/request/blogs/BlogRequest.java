package aptech.project.educhain.request.blogs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogRequest {
    private Integer userId;

    @NotNull
    private Integer blogCategoryId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String blogText;
}
