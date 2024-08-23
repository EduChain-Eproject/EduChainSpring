package aptech.project.educhain.endpoint.requests.accounts;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Email(message = "Invalid email")
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "firstName is required")
    private String firstName;
    @NotEmpty(message = "lastName is required")
    private String lastName;
    @NotEmpty(message = "phone is required")
    private String phone;
    private String address;
    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{7,}$", message = "Password must have at least 7 characters, at least 1 uppercase letter, at least 1 digit, and no special characters")
    private String password;
    @NotEmpty(message = "accountType is required")
    private String accountType;
}
