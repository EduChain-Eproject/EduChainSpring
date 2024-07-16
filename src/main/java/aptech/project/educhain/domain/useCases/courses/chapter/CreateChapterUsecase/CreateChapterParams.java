package aptech.project.educhain.domain.useCases.courses.chapter.CreateChapterUsecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChapterParams {
    private Integer courseId;
    private String chapterTitle;
}