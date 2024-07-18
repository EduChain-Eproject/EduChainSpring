package aptech.project.educhain.data.entities.courses;

import java.util.ArrayList;
import java.util.List;

import aptech.project.educhain.data.entities.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_id", referencedColumnName = "id")
    private Homework homework;

    @Column(name = "questionText", columnDefinition = "TEXT")
    private String questionText;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "correct_answer_id")
    private Answer correctAnswer;

    @PrePersist
    public void prePersist() {
        if (answers.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                Answer answer = new Answer();
                answer.setQuestion(this);
                answer.setAnswerText("Answer " + (i + 1));
                answers.add(answer);
            }
        }
        if (this.correctAnswer == null) {
            this.correctAnswer = answers.get(0);
        }
    }
}
