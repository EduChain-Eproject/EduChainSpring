package aptech.project.educhain.domain.useCases.courses.lesson.DeleteLessonUsecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.repositories.courses.LessonRepository;

@Component
public class DeleteLessonUsecase implements Usecase<Void, Integer> {

    @Autowired
    LessonRepository lessonRepository;

    @Override
    public AppResult<Void> execute(Integer lessonId) {
        try {
            if (lessonRepository.existsById(lessonId)) {
                lessonRepository.deleteById(lessonId);
                return AppResult.successResult(null);
            } else {
                return AppResult.failureResult(new Failure("Lesson not found with ID: " + lessonId));
            }
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error deleting lesson: " + e.getMessage()));
        }
    }
}
