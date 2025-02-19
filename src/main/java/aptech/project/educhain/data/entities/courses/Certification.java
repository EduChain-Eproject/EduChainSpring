package aptech.project.educhain.data.entities.courses;

import java.time.LocalDateTime;
import lombok.Data;

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

@Entity
@Data
@Table(name = "tbl_certifications")
public class Certification extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CertificationStatus status;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "transaction_hash", length = 66)
    private String transactionHash;

    @Column(name = "nft_token_id", unique = true)
    private String nftTokenId;
}
