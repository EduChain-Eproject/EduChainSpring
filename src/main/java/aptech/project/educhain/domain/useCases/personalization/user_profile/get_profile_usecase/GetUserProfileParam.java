package aptech.project.educhain.domain.useCases.personalization.user_profile.get_profile_usecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserProfileParam {
    private String email;
}
