package aptech.project.educhain.domain.dtos.courses;

import java.sql.Timestamp;
import java.util.List;

import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.UserCourse;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<CategoryDTO> categoryList;
    private String avatarPath;
}
