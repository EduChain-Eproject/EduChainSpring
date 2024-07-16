package aptech.project.educhain.data.entities.courses;

import java.time.LocalDateTime;

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

@Entity
@Table(name = "tbl_awards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Award extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_id", referencedColumnName = "id")
    private Homework homework;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AwardStatus status; // PENDING, APPROVED, REJECTED

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments; // Comments from the teacher
}
