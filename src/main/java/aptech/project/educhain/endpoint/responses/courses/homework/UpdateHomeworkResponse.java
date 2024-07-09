package aptech.project.educhain.endpoint.responses.courses.homework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHomeworkResponse {
    private Integer id;
    private Integer userID;
    private Integer lessonID;
    private String title;
    private String description;
}