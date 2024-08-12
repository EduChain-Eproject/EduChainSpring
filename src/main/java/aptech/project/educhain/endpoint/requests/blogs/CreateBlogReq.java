package aptech.project.educhain.endpoint.requests.blogs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
@Data

public class CreateBlogReq {
    @NotNull(message = "Id cannot be null")
    private Integer userId;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotNull(message = "Blog category ID is required")
    @Min(value = 1, message = "Blog category must be selected")
    private Integer blogCategoryId;

    @NotBlank(message = "Blog text is required")
    private String blogText;

    @NotNull(message = "Photo is required")
    private MultipartFile photo;
}
