package aptech.project.educhain.domain.useCases.personalization.user_profile.update_profile_usecase;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserProfileParam {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String avatarPath;
    private MultipartFile avatarFile;
}
