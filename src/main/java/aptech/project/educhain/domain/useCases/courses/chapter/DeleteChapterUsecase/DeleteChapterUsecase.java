package aptech.project.educhain.domain.useCases.courses.chapter.DeleteChapterUsecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.repositories.courses.ChapterRepository;

@Component
public class DeleteChapterUsecase implements Usecase<Void, Integer> {

    @Autowired
    ChapterRepository chapterRepository;

    @Override
    public AppResult<Void> execute(Integer chapterId) {
        try {
            if (chapterRepository.existsById(chapterId)) {
                chapterRepository.deleteById(chapterId);
                return AppResult.successResult(null);
            } else {
                return AppResult.failureResult(new Failure("Chapter not found with ID: " + chapterId));
            }
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error deleting chapter: " + e.getMessage()));
        }
    }
}
