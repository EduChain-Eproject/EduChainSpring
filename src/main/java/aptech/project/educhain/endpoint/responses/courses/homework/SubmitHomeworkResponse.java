package aptech.project.educhain.endpoint.responses.courses.homework;

import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.dtos.courses.CertificationDTO;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitHomeworkResponse {
    UserHomeworkDTO submission;
    AwardDTO award;
    CertificationDTO CertificationDto;
}
