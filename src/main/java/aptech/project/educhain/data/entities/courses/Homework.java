package aptech.project.educhain.data.entities.courses;

import java.util.List;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.accounts.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_homeworks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Homework extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "homework", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "homework", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers;

    @OneToMany(mappedBy = "homework", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Award> userAwards;

    @OneToMany(mappedBy = "homework", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserHomework> userHomeworks;
}
