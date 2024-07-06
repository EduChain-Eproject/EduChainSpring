package aptech.project.educhain.data.entities.courses;

import aptech.project.educhain.data.entities.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_answers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answers extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;

    @Column(name = "answerText", columnDefinition = "TEXT")
    private String answerText;

    @OneToMany(mappedBy = "answer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers;

    public Answers(String answerText){
        this.answerText = answerText;
    }
}
