package aptech.project.educhain.domain.services.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;
import aptech.project.educhain.domain.useCases.courses.lesson.CreateLessonUsecase.CreateLessonParams;
import aptech.project.educhain.domain.useCases.courses.lesson.GetLessonDetailUsecase.GetLessonDetailParams;
import aptech.project.educhain.domain.useCases.courses.lesson.UpdateLessonUsecase.UpdateLessonParams;

public interface ILessonService {

    AppResult<LessonDTO> getLessonDetail(GetLessonDetailParams params);

    AppResult<LessonDTO> createLesson(CreateLessonParams params);

    AppResult<LessonDTO> updateLesson(UpdateLessonParams params);

    AppResult<Void> deleteLesson(Integer lessonId);
}
