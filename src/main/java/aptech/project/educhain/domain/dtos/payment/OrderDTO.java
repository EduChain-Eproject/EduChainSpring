package aptech.project.educhain.domain.dtos.payment;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer id;
    private Timestamp createdAt;
    private Integer userId;
    private Integer courseId;
    private BigDecimal amount;

    private UserDTO user;
    private CourseDTO course;
}
