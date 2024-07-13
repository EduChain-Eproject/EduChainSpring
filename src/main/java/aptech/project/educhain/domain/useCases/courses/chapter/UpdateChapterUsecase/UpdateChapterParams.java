package aptech.project.educhain.domain.useCases.courses.chapter.UpdateChapterUsecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChapterParams {
    private Integer id;
    private String chapterTitle;
}
