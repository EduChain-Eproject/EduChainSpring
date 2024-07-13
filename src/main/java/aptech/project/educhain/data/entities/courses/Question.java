package aptech.project.educhain.data.entities.courses;

import aptech.project.educhain.data.entities.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private List<Answers> answers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "correct_answer_id")
    private Answers correctAnswer;

    @PrePersist
    public void prePersist() {
        if (answers.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                Answers answer = new Answers();
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

