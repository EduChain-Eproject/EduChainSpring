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
public class CreateLessonRequest {
    @NotNull(message = "cant recognize chapter")
    private Integer chapterId;
    @NotEmpty(message = "lessonTitle required")
    private String lessonTitle;
    @NotEmpty(message = "description required")
    private String description;
    @NotEmpty(message = "videoTitle required")
    private String videoTitle;
    private String videoURL;
    @NotNull(message = "Video file is required")
    private MultipartFile videoFile;
}