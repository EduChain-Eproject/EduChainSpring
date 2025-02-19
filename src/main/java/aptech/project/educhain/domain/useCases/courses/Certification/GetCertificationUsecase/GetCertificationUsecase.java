package aptech.project.educhain.domain.useCases.courses.Certification.GetCertificationUsecase;

import java.util.Optional;

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
public class GetCertificationUsecase implements Usecase<CertificationDTO, Integer> {

    @Autowired
    CertificationRepository certificationRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<CertificationDTO> execute(Integer certificationId) {
        try {
            Optional<Certification> CertificationOptional = certificationRepository.findById(certificationId);

            if (CertificationOptional.isPresent()) {

                Certification certification = CertificationOptional.get();

                CertificationDTO certificationDTO = modelMapper.map(certification, CertificationDTO.class);
                certificationDTO.setUserDto(modelMapper.map(certification.getUser(), UserDTO.class));
                certificationDTO.setCourseDTO(modelMapper.map(certification.getCourse(), CourseDTO.class));

                return AppResult.successResult(certificationDTO);
            } else {
                return AppResult.failureResult(new Failure("no Certification!"));
            }
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get certification: " + e.getMessage()));
        }
    }

}
