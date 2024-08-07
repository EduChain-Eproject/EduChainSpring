package aptech.project.educhain.endpoint.requests.courses.lesson.teacher;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLessonRequest {

    @NotEmpty(message = "Lesson title is required")
    private String lessonTitle;
    @NotEmpty(message = "Description is required")
    private String description;
    @NotEmpty(message = "Video title is required")
    private String videoTitle;
    private String videoURL;
    @NotNull(message = "videoFile is required")
    private MultipartFile videoFile;

}