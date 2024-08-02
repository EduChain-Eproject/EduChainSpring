package aptech.project.educhain.endpoint.requests.courses.chapter.teacher;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChapterRequest {
    @NotEmpty(message = "chapter title required")
    private String chapterTitle;
}
