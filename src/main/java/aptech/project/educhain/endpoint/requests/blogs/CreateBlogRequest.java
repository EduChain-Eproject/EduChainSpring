package aptech.project.educhain.endpoint.requests.blogs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlogRequest {
    @NotEmpty(message = "Title is required")
    private String title;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Blog Category ID is required")
    private Integer blogCategoryId;

    @NotEmpty(message = "Blog text is required")
    private String blogText;


    private MultipartFile photo;
}