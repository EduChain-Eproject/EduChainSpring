package aptech.project.educhain.endpoint.responses.courses.course.student;

import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCourseResponse {

    private Integer id;
    private String title;
    private String description;
    private Double price;
    private String status;
    private Integer numberOfLessons;
    private UserCourseDTO currentUserCourse;
}
