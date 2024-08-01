package aptech.project.educhain.endpoint.requests.courses.chapter.teacher;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChapterRequest {
    @NotNull(message = "can recognize course")
    private Integer courseId;
    @NotEmpty(message = "chapter title require")
    private String chapterTitle;
}