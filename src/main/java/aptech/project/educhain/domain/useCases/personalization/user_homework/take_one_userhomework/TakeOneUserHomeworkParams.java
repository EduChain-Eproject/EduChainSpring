package aptech.project.educhain.domain.useCases.personalization.user_homework.take_one_userhomework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TakeOneUserHomeworkParams {
    private int student_id;
    private int homework_id;
}
