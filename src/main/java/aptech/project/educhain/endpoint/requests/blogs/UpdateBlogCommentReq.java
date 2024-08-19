package aptech.project.educhain.endpoint.requests.blogs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data

public class UpdateBlogCommentReq {
    @NotBlank(message = "Text is required")
    private String text;
}
