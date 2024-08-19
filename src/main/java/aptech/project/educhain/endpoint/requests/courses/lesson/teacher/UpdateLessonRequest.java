package aptech.project.educhain.endpoint.requests.courses.lesson.teacher;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLessonRequest {
    private Integer chapterId;

    @NotEmpty(message = "Lesson title is required")
    private String lessonTitle;
    @NotEmpty(message = "Description is required")
    private String description;
    @NotEmpty(message = "Video title is required")
    private String videoTitle;
    @NotNull(message = "videoFile is required")
    private MultipartFile videoFile;

}