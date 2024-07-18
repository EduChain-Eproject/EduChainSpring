package aptech.project.educhain.domain.useCases.courses.UserHomework.GetUserHomeworkUseCase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserHomeworkParams {
    Integer userId;
    Integer homeworkId;
}
