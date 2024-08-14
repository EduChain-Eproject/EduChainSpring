package aptech.project.educhain.domain.dtos.UserInterests;

import aptech.project.educhain.data.entities.courses.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInterestsDTO {
    private int student_id;
    private int course_id;
    private String description;
    private String title;
    private Double price;
    private String teacherName;
    private List<Category> categoryList;
}
