package aptech.project.educhain.endpoint.requests.courses.chapter.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChapterRequest {
    private Integer courseId;
    private String chapterTitle;
}