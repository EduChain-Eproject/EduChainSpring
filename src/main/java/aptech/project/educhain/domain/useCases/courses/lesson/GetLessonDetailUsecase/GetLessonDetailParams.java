package aptech.project.educhain.domain.useCases.courses.lesson.GetLessonDetailUsecase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetLessonDetailParams {

    private Integer userId;
    private Integer lessonId;
}
