package aptech.project.educhain.data.entities.courses;

import aptech.project.educhain.data.entities.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_lessons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lesson extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", referencedColumnName = "id")
    private Chapter chapter;

    @Column(name = "lessonTitle", length = 100)
    private String lessonTitle;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "videoTitle", length = 100)
    private String videoTitle;

    @Column(name = "videoURL", length = 200)
    private String videoURL;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Homework> homeworks;
}
