package aptech.project.educhain.domain.useCases.account.wishlist.add_to_user_interests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToUserInterestsParams {
    private int user_id;
    private int course_id;
    private int id;
}
