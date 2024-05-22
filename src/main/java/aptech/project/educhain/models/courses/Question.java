package aptech.project.educhain.models.courses;

import aptech.project.educhain.models.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Answers> answers;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers;
}
