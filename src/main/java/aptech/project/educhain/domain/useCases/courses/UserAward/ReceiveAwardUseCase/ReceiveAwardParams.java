package aptech.project.educhain.domain.useCases.courses.UserAward.ReceiveAwardUseCase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceiveAwardParams {
    Integer userId;
    Integer awardId;
}
