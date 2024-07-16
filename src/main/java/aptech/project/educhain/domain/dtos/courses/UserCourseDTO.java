package aptech.project.educhain.domain.dtos.courses;

import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.UserCourse;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@Getter
@Setter
public class UserCourseDTO {
    private String teacherName;
    private String teacherEmail;
    private String title;
    private Timestamp enrollmentDate;
    private Double price;
    private UserCourse.CompletionStatus completionStatus;
    private List<Category> categoryList;

    public UserCourseDTO(String teacherName, String teacherEmail, String title, Timestamp enrollmentDate,
                         Double price, UserCourse.CompletionStatus completionStatus, List<Category> categoryList) {
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.title = title;
        this.enrollmentDate = enrollmentDate;
        this.price = price;
        this.completionStatus = completionStatus;
        this.categoryList = categoryList;
    }
}
