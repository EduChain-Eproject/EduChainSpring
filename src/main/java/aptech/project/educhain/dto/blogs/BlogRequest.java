package aptech.project.educhain.dto.blogs;

import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.blogs.BlogCategory;
import aptech.project.educhain.models.blogs.BlogComment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class BlogRequest {
    private User user;

    @NotEmpty
    private BlogCategory blogCategory;

    @NotEmpty
    private String title;

    @NotEmpty
    private String blogText;
}
