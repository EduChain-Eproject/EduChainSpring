package aptech.project.educhain.domain.useCases.courses.UserAward.ApproveOrRejectAwardUseCase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApproveOrRejectAwardParams {
    private Integer awardId;
    private Integer teacherId;
    private TeacherUpdatingAwardStatus updatingAwardStatus;

    /**
     * TeacherUpdatingAwardStatus
     */
    public enum TeacherUpdatingAwardStatus {
        APPROVE,
        REJECT,
    }
}
