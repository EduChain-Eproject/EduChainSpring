package aptech.project.educhain.domain.useCases.account.user_interest.get_all_user_interest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInterestByUserIdParams {
    private int id;
    private int user_id;
}
