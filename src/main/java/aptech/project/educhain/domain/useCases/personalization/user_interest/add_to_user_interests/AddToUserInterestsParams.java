package aptech.project.educhain.domain.useCases.personalization.user_interest.add_to_user_interests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToUserInterestsParams {

    private int student_id;
    private int course_id;
}
