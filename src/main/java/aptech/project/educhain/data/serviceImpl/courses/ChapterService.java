package aptech.project.educhain.data.serviceImpl.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.services.courses.IChapterService;
import aptech.project.educhain.domain.useCases.courses.chapter.CreateChapterUsecase.CreateChapterParams;
import aptech.project.educhain.domain.useCases.courses.chapter.CreateChapterUsecase.CreateChapterUsecase;
import aptech.project.educhain.domain.useCases.courses.chapter.DeleteChapterUsecase.DeleteChapterUsecase;
import aptech.project.educhain.domain.useCases.courses.chapter.UpdateChapterUsecase.UpdateChapterParams;
import aptech.project.educhain.domain.useCases.courses.chapter.UpdateChapterUsecase.UpdateChapterUsecase;

@Service
public class ChapterService implements IChapterService {
    @Autowired
    CreateChapterUsecase createChapterUsecase;

    @Autowired
    UpdateChapterUsecase updateChapterUsecase;

    @Autowired
    DeleteChapterUsecase deleteChapterUsecase;

    @Override
    public AppResult<ChapterDTO> createChapter(CreateChapterParams params) {
        return createChapterUsecase.execute(params);
    }

    @Override
    public AppResult<ChapterDTO> updateChapter(UpdateChapterParams params) {
        return updateChapterUsecase.execute(params);
    }

    @Override
    public AppResult<Void> deleteChapter(Integer chapterId) {
        return deleteChapterUsecase.execute(chapterId);
    }
}
