package aptech.project.educhain.domain.useCases.courses.lesson.CreateLessonUsecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLessonParams {
    private Integer chapterId;
    private String lessonTitle;
    private String description;
    private String videoTitle;
    private MultipartFile videoFile;
}