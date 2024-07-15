package aptech.project.educhain.data.entities.courses;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.accounts.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "tbl_user_course")
@Data
@AllArgsConstructor
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

    public UserCourse() {
        this.progress = BigDecimal.ZERO; // Set default value to BigDecimal.ZERO
    }
}
