package aptech.project.educhain.domain.dtos.payment;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.others.Coupon;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class OrderDTO {
    private User user;
    private Course course;
    private Float amount;
}
