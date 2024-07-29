package aptech.project.educhain.endpoint.responses.payment.order;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

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
