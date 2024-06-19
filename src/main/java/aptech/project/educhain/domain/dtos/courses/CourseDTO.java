package aptech.project.educhain.domain.dtos.courses;

import java.util.List;

import lombok.Data;

@Data
public class CourseDTO {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private String status;

    // private UserDTO teacher;

    private List<CategoryDTO> categoryDtos;

    // private List<Chapter> chapters;

    // private List<UserCourse> participatedUsers;

    // private List<UserInterest> userInterests;

    // private List<CourseFeedback> courseFeedbacks;
}