package aptech.project.educhain.domain.useCases.courses.Certification.ApproveOrRejectCertification;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ApproveOrRejectCertificationParams {
    private Integer certificationId;
    private String comments;
    private Integer teacherId;
    private TeacherUpdatingCertificationStatus updatingCertificationStatus;

    /**
     * TeacherUpdatingCertificationStatus
     */
    public enum TeacherUpdatingCertificationStatus {
        APPROVE,
        REJECT,
    }
}
