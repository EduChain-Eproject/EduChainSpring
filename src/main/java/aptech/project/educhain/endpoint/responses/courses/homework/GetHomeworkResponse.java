package aptech.project.educhain.endpoint.responses.courses.homework;

import lombok.Data;

@Data
public class GetHomeworkResponse {
    private Integer id;
    private Integer lessonID;
    private String title;
    private String description;
}
