package aptech.project.educhain.domain.useCases.courses.Certification.GetUserCertificationUsecase;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Certification;
import aptech.project.educhain.data.repositories.courses.CertificationRepository;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CertificationDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import org.springframework.stereotype.Component;

@Component
public class GetUserCertificationUsecase implements Usecase<CertificationDTO, GetUserCertificationParams> {

    @Autowired
    CertificationRepository certificationRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<CertificationDTO> execute(GetUserCertificationParams params) {
        try {
            Certification certifications = certificationRepository.findByUserIdAndCourseId(params.getUserId(),
                    params.getCourseId());

            CertificationDTO certificationDto = modelMapper.map(certifications, CertificationDTO.class);
            certificationDto.setUserDto(modelMapper.map(certifications.getUser(), UserDTO.class));
            certificationDto.setCourseDTO(modelMapper.map(certifications.getCourse(), CourseDTO.class));

            return AppResult.successResult(certificationDto);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to retrieve certifications: " + e.getMessage()));
        }
    }

}
