package aptech.project.educhain.request.accounts;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "Email is required")
    private String email;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{7,}$",
            message = "Password must have at least 7 characters, at least 1 uppercase letter, at least 1 digit, and no special characters"
    )
    @NotEmpty(message = "Password is required")
    private String password;
}
