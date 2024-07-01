package aptech.project.educhain.domain.dtos.UserProfile;

import aptech.project.educhain.data.entities.accounts.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private Role role;
    private String avatarPath;
}
