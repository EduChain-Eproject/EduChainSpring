package aptech.project.educhain.domain.useCases.courses.Certification.CheckProgressAndCertify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckProgressParams {
    private Integer studentId;
    private Integer homeworkId;
}