package aptech.project.educhain.endpoint.requests.personaliztion.user_profile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @NotEmpty(message = "cant recognize your firstName")
    private String firstName;
    @NotEmpty(message = "cant recognize your lastName")
    private String lastName;
    @NotEmpty(message = "cant recognize your phone")
    private String phone;
    @NotEmpty(message = "cant recognize your address")
    private String address;
    private MultipartFile avatarFile;
}