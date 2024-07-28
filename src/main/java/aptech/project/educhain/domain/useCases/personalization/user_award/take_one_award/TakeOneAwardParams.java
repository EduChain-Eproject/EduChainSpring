package aptech.project.educhain.domain.useCases.personalization.user_award.take_one_award;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TakeOneAwardParams {
    private int student_id;
    private int Award_id;
}
