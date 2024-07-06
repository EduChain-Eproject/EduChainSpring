package aptech.project.educhain.endpoint.requests.courses.lesson.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLessonRequest {
    private String lessonTitle;
    private String description;
    private String videoTitle;
    private String videoURL;
}