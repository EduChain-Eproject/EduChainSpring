package aptech.project.educhain.data.entities.courses;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.accounts.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "progress")
    private Double progress; // This can be a percentage or any metric that makes sense

    @Column(name = "grade")
    private BigDecimal grade;

    @Column(name = "isSubmitted")
    private Boolean isSubmitted;

    @OneToMany(mappedBy = "userHomework", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers;
}
