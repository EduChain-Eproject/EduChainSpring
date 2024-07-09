package aptech.project.educhain.endpoint.responses.courses.homework;

import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHomeworkResponse {
    private Integer id;
    private Integer userID;
    private Integer lessonID;
    private String title;
    private String description;
}