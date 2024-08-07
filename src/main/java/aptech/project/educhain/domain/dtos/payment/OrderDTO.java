package aptech.project.educhain.domain.dtos.payment;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.others.Coupon;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
