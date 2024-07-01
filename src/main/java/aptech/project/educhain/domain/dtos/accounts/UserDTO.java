package aptech.project.educhain.domain.dtos.accounts;

import aptech.project.educhain.data.entities.accounts.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String avatarPath;
    private String phone;
    private String address;
    private Role role;
}
