package aptech.project.educhain.models.courses;

import aptech.project.educhain.models.BaseModel;
import aptech.project.educhain.models.accounts.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "commentText", columnDefinition = "TEXT")
    private String commentText;
}
