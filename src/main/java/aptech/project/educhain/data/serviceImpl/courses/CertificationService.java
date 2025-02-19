package aptech.project.educhain.data.serviceImpl.courses;

import org.springframework.beans.factory.annotation.Autowired;
import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.CertificationDTO;
import aptech.project.educhain.domain.services.courses.ICertificationService;
import aptech.project.educhain.domain.useCases.courses.Certification.ApproveOrRejectCertification.ApproveOrRejectCertificationParams;
import aptech.project.educhain.domain.useCases.courses.Certification.ApproveOrRejectCertification.ApproveOrRejectCertificationUsecase;
import aptech.project.educhain.domain.useCases.courses.Certification.GetCertificationUsecase.GetCertificationUsecase;
import aptech.project.educhain.domain.useCases.courses.Certification.GetUserCertificationUsecase.GetUserCertificationParams;
import aptech.project.educhain.domain.useCases.courses.Certification.GetUserCertificationUsecase.GetUserCertificationUsecase;
import org.springframework.stereotype.Service;

@Service
public class CertificationService implements ICertificationService {

    @Autowired
    GetCertificationUsecase getCertificationUsecase;

    @Autowired
    GetUserCertificationUsecase getUserCertificationUsecase;

    @Autowired
    ApproveOrRejectCertificationUsecase approveOrRejectCertificationUsecase;

    @Override
    public AppResult<CertificationDTO> getCertification(Integer certificationId) {
        return getCertificationUsecase.execute(certificationId);
    }

    @Override
    public AppResult<CertificationDTO> getUserCertification(GetUserCertificationParams params) {
        return getUserCertificationUsecase.execute(params);
    }

    @Override
    public AppResult<CertificationDTO> approveRejectCertification(ApproveOrRejectCertificationParams params) {
        return approveOrRejectCertificationUsecase.execute(params);
    }

}
