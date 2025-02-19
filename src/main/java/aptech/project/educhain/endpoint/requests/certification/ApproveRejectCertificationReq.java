package aptech.project.educhain.endpoint.requests.certification;

import aptech.project.educhain.domain.useCases.courses.Certification.ApproveOrRejectCertification.ApproveOrRejectCertificationParams.TeacherUpdatingCertificationStatus;
import lombok.Data;

@Data
public class ApproveRejectCertificationReq {
    private String comments;
    private TeacherUpdatingCertificationStatus updatingCertificationStatus;
}
