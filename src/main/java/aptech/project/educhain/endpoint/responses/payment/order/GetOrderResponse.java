package aptech.project.educhain.endpoint.responses.payment.order;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class GetOrderResponse {
    private Integer id;
    private Timestamp createdAt;
    private Integer userId;
    private Integer courseId;
    private BigDecimal amount;

    private UserDTO user;
    private CourseDTO course;
}
