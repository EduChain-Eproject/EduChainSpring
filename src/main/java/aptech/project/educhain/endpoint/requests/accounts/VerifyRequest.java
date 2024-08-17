package aptech.project.educhain.endpoint.requests.accounts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyRequest {
    @NotNull(message = "code is required")
    private Integer code;
    @NotBlank(message = "you need register first")
    private String email;
}
