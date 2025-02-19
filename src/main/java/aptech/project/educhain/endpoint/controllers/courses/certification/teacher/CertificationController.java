package aptech.project.educhain.endpoint.controllers.courses.certification.teacher;

import aptech.project.educhain.common.result.ApiError;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.serviceImpl.courses.CertificationService;
import aptech.project.educhain.domain.dtos.courses.CertificationDTO;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.useCases.courses.Certification.ApproveOrRejectCertification.ApproveOrRejectCertificationParams;
import aptech.project.educhain.endpoint.requests.certification.ApproveRejectCertificationReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "TeacherCertification")
@RestController("TeacherCertification")
@CrossOrigin
@RequestMapping("/TEACHER/api/certification")
public class CertificationController {
    @Autowired
    CertificationService CertificationService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IJwtService iJwtService;

    @Operation(summary = "approve/reject an Certification")
    @PostMapping("approve_or_reject/{certification_id}")
    public ResponseEntity<?> approveOrReject(
            @PathVariable Integer certification_id,
            @RequestBody ApproveRejectCertificationReq bodyReq,
            HttpServletRequest request) {
        var user = iJwtService.getUserByHeaderToken(request.getHeader("Authorization"));

        AppResult<CertificationDTO> result = CertificationService.approveRejectCertification(
                new ApproveOrRejectCertificationParams(certification_id, bodyReq.getComments(), user.getId(),
                        bodyReq.getUpdatingCertificationStatus()));

        if (result.isSuccess()) {
            return ResponseEntity.ok().body(result.getSuccess());
        }
        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()), HttpStatus.OK);
    }
}
