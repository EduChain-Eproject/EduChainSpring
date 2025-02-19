package aptech.project.educhain.endpoint.controllers.courses.certification.shared;

import aptech.project.educhain.common.result.ApiError;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.serviceImpl.courses.CertificationService;
import aptech.project.educhain.domain.dtos.courses.CertificationDTO;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "SharedStudentCertification")
@RestController("SharedStudentCertification")
@CrossOrigin
@RequestMapping("/SHARED/api/certification")
public class CertificationController {
    @Autowired
    CertificationService certificationService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("detail/{certification_id}")
    public ResponseEntity<?> detail(@PathVariable Integer certification_id) {
        AppResult<CertificationDTO> result = certificationService.getCertification(certification_id);

        if (result.isSuccess()) {
            return ResponseEntity.ok().body(result.getSuccess());
        }

        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()), HttpStatus.OK);
    }
}
