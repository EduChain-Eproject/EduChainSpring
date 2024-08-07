package aptech.project.educhain.endpoint.responses.admin;

import aptech.project.educhain.data.entities.accounts.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListUserResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String avatarPath;
    private String phone;
    private String address;
    private Role role;
    private String email;
    private Boolean isActive;
}
