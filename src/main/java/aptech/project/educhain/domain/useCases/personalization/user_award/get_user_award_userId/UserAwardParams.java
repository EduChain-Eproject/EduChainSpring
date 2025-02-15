package aptech.project.educhain.domain.useCases.personalization.user_award.get_user_award_userId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardParams {
    int page;
    int size;
    int userId;
}
