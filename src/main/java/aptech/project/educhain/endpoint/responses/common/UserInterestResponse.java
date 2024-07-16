package aptech.project.educhain.endpoint.responses.common;

import aptech.project.educhain.data.entities.courses.Category;
import lombok.Data;

import java.util.List;
@Data
public class UserInterestResponse {
    private int course_id;
    private int user_id;
    private String description;
    private String title;
    private Double price;
    private String teacherName;
    private List<Category> categoryList;
}
