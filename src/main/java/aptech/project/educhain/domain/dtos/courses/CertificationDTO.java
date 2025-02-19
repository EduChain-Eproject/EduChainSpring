package aptech.project.educhain.domain.dtos.courses;

import java.time.LocalDateTime;
import lombok.Data;

import aptech.project.educhain.data.entities.courses.CertificationStatus;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;

@Data
public class CertificationDTO {
    private Integer id;
    private UserDTO userDto;
    private CourseDTO courseDTO;
    private CertificationStatus status;
    private LocalDateTime issueDate;
    private String comments;
    private String transactionHash;
    private String nftTokenId;
}
