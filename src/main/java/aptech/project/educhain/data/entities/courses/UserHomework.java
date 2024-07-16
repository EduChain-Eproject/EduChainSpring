package aptech.project.educhain.data.entities.courses;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.accounts.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_user_homework")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHomework extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_id", referencedColumnName = "id")
    private Homework homework;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "submissionDate")
    private Date submissionDate;

    @Column(name = "grade")
    private BigDecimal grade;

    @Column(name = "isSubmitted")
    private Boolean isSubmitted;
}
