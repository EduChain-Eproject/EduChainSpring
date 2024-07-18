package aptech.project.educhain.endpoint.requests.award;

import aptech.project.educhain.domain.useCases.courses.UserAward.ApproveOrRejectAwardUseCase.ApproveOrRejectAwardParams.TeacherUpdatingAwardStatus;
import lombok.Data;

@Data
public class ApproveRejectAwardReq {
    private TeacherUpdatingAwardStatus updatingAwardStatus;
}
