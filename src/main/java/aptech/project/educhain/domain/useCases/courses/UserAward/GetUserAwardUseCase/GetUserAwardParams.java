package aptech.project.educhain.domain.useCases.courses.UserAward.GetUserAwardUseCase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserAwardParams {
    Integer userId;
    Integer homeworkId;
}
