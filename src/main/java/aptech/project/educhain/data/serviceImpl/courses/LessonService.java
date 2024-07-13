package aptech.project.educhain.data.serviceImpl.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;
import aptech.project.educhain.domain.services.courses.ILessonService;
import aptech.project.educhain.domain.useCases.courses.lesson.CreateLessonUsecase.CreateLessonParams;
import aptech.project.educhain.domain.useCases.courses.lesson.CreateLessonUsecase.CreateLessonUsecase;
import aptech.project.educhain.domain.useCases.courses.lesson.DeleteLessonUsecase.DeleteLessonUsecase;
import aptech.project.educhain.domain.useCases.courses.lesson.GetLessonDetailUsecase.GetLessonDetailUsecase;
import aptech.project.educhain.domain.useCases.courses.lesson.UpdateLessonUsecase.UpdateLessonParams;
import aptech.project.educhain.domain.useCases.courses.lesson.UpdateLessonUsecase.UpdateLessonUsecase;

@Service
public class LessonService implements ILessonService {

    @Autowired
    GetLessonDetailUsecase getLessonDetailUsecase;

    @Autowired
    CreateLessonUsecase createLessonUsecase;

    @Autowired
    UpdateLessonUsecase updateLessonUsecase;

    @Autowired
    DeleteLessonUsecase deleteLessonUsecase;

    @Override
    public AppResult<LessonDTO> getLessonDetail(Integer lessonId) {
        return getLessonDetailUsecase.execute(lessonId);
    }

    @Override
    public AppResult<LessonDTO> createLesson(CreateLessonParams params) {
        return createLessonUsecase.execute(params);
    }

    @Override
    public AppResult<LessonDTO> updateLesson(UpdateLessonParams params) {
        return updateLessonUsecase.execute(params);
    }

    @Override
    public AppResult<Void> deleteLesson(Integer lessonId) {
        return deleteLessonUsecase.execute(lessonId);
    }
}
