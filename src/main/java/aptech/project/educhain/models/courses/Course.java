package aptech.project.educhain.models.courses;

import aptech.project.educhain.models.BaseModel;
import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.accounts.UserInterest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "courseCategory_id", referencedColumnName = "id", nullable = true)
    private CourseCategory courseCategory;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User teacher;

    @Column(name = "isActive")
    private Boolean isActive;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserCourse> userCourses;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserInterest> userInterests;
}
