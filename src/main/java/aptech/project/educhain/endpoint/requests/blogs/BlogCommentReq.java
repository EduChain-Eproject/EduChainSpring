package aptech.project.educhain.endpoint.requests.blogs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
@Data
public class BlogCommentReq {
        @NotBlank(message = "Text is required")
        private String text;
        @NotNull(message = "User ID is required")
        private Integer userId;
        private Integer commentId;
        @NotNull(message = "Blog ID is required")
        private Integer blogId;
}
