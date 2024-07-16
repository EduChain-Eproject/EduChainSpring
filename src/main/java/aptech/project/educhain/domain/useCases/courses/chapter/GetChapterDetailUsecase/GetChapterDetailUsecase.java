package aptech.project.educhain.domain.useCases.courses.chapter.GetChapterDetailUsecase;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Chapter;
import aptech.project.educhain.data.repositories.courses.ChapterRepository;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;

@Component
public class GetChapterDetailUsecase implements Usecase<ChapterDTO, GetChapterDetailParams> {
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppResult<ChapterDTO> execute(GetChapterDetailParams params) {
        try {
            var chapterOptional = chapterRepository.findById(params.getChapterId());
            if (!chapterOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Chapter not found"));
            }

            Chapter chapter = chapterOptional.get();
            ChapterDTO chapterDTO = modelMapper.map(chapter, ChapterDTO.class);
            chapterDTO.setLessonDtos(chapter.getLessons().stream()
                    .map(lesson -> modelMapper.map(lesson, LessonDTO.class))
                    .collect(Collectors.toList()));
            chapterDTO.setCourseDto(modelMapper.map(chapter.getCourse(), CourseDTO.class));

            return AppResult.successResult(chapterDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get chapter detail: " + e.getMessage()));
        }
    }
}
