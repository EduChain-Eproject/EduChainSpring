package aptech.project.educhain.endpoint.requests.blogs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
@Data

public class CreateBlogReq {
    private Integer UserId;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotNull(message = "Blog category ID is required")
    private Integer blogCategoryId;

    @NotBlank(message = "Blog text is required")
    private String blogText;

    private MultipartFile photo;
}
