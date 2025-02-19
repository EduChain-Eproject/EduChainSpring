package aptech.project.educhain.domain.useCases.courses.Certification.GetUserCertificationUsecase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserCertificationParams {
    private Integer userId;
    private Integer courseId;
}
