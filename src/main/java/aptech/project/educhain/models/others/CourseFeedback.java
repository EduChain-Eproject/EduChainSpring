package aptech.project.educhain.models.others;

import aptech.project.educhain.models.BaseModel;
import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.accounts.UserInterest;
import aptech.project.educhain.models.courses.Chapter;
import aptech.project.educhain.models.courses.Course;
import aptech.project.educhain.models.courses.CourseCategory;
import aptech.project.educhain.models.courses.UserCourse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tbl_course_feedbacks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseFeedback extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating;
}
