package aptech.project.educhain.domain.useCases.personalization.user_interest.get_all_user_interest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInterestByUserIdParams {
    private int user_id;
    private int page ;
    private int size ;
    private String titleSearch;
}
