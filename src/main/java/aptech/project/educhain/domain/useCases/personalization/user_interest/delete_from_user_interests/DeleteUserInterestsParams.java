package aptech.project.educhain.domain.useCases.personalization.user_interest.delete_from_user_interests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserInterestsParams {
    private int student_id;
    private int course_id;
}
