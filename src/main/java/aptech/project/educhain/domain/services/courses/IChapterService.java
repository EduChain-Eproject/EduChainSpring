package aptech.project.educhain.domain.services.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.useCases.courses.chapter.CreateChapterUsecase.CreateChapterParams;
import aptech.project.educhain.domain.useCases.courses.chapter.GetChapterDetailUsecase.GetChapterDetailParams;
import aptech.project.educhain.domain.useCases.courses.chapter.UpdateChapterUsecase.UpdateChapterParams;

public interface IChapterService {
    AppResult<ChapterDTO> createChapter(CreateChapterParams params);

    AppResult<ChapterDTO> updateChapter(UpdateChapterParams params);

    AppResult<Void> deleteChapter(Integer chapterId);

    AppResult<ChapterDTO> getChapterDetail(GetChapterDetailParams params);
}
