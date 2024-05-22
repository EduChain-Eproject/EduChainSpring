package aptech.project.educhain.models.courses;

import aptech.project.educhain.models.BaseModel;
import aptech.project.educhain.models.accounts.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_user_answers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswer extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    private Answers answer;

    @Column(name = "isCorrect")
    private Boolean isCorrect;
}
