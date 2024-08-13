package aptech.project.educhain.data.entities.courses;

import java.math.BigDecimal;
import java.sql.Timestamp;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.accounts.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_user_course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourse extends BaseModel {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(name = "enrollmentDate")
    private Timestamp enrollmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "completionStatus")
    private CompletionStatus completionStatus;

    @Column(name = "progress")
    private BigDecimal progress;

    public enum CompletionStatus {
        NOT_STARTED, IN_PROGRESS, COMPLETED
    }
}
