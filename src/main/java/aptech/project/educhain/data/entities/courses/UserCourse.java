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
@Table(name = "tbl_user_course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourse extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course", referencedColumnName = "id")
    private Course course;

    @Column(name = "enrollmentDate")
    private Date enrollmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "completionStatus")
    private CompletionStatus completionStatus;

    @Column(name = "progress")
    private BigDecimal progress;

    public enum CompletionStatus {
        NOT_STARTED, IN_PROGRESS, COMPLETED
    }
}
