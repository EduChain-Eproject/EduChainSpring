package aptech.project.educhain.domain.useCases.courses.lesson.CreateLessonUsecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLessonParams {
    private Integer chapterId;
    private String lessonTitle;
    private String description;
    private String videoTitle;
    private String videoURL;
}