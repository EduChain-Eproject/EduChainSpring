package aptech.project.educhain.models.courses;

import aptech.project.educhain.models.BaseModel;
import aptech.project.educhain.models.accounts.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_chapters")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    private String chapterTitle;

    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lesson> lessons;
}
