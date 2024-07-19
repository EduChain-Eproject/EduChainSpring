package aptech.project.educhain.domain.useCases.courses.UserHomework.SubmitHomeworkUseCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitHomeworkParams {
    private Integer userId;
    private Integer homeworkId;
}
