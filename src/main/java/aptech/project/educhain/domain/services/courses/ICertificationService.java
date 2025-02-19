package aptech.project.educhain.domain.services.courses;

import java.util.List;
import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.CertificationDTO;
import aptech.project.educhain.domain.useCases.courses.Certification.ApproveOrRejectCertification.ApproveOrRejectCertificationParams;
import aptech.project.educhain.domain.useCases.courses.Certification.GetUserCertificationUsecase.GetUserCertificationParams;

public interface ICertificationService {
    AppResult<CertificationDTO> getCertification(Integer certificationId);

    AppResult<CertificationDTO> getUserCertification(GetUserCertificationParams params);

    AppResult<CertificationDTO> approveRejectCertification(ApproveOrRejectCertificationParams params);
}
