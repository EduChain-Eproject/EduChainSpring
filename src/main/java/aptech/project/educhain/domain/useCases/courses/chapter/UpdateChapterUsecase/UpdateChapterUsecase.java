package aptech.project.educhain.domain.useCases.courses.chapter.UpdateChapterUsecase;

import java.util.List;
import java.util.Optional;
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
public class UpdateChapterUsecase implements Usecase<ChapterDTO, UpdateChapterParams> {

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<ChapterDTO> execute(UpdateChapterParams params) {
        try {
            Optional<Chapter> chapterOptional = chapterRepository.findById(params.getId());
            if (!chapterOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Chapter not found with ID: " + params.getId()));
            }

            Chapter chapter = chapterOptional.get();
            chapter.setChapterTitle(params.getChapterTitle());

            Chapter updatedChapter = chapterRepository.saveAndFlush(chapter);
            ChapterDTO chapterDTO = modelMapper.map(updatedChapter, ChapterDTO.class);
            chapterDTO.setCourseDto(modelMapper.map(updatedChapter.getCourse(), CourseDTO.class));

            var listLessons = updatedChapter.getLessons();
            List<LessonDTO> lessonDtos = listLessons != null ? listLessons.stream()
                    .map(lesson -> {
                        LessonDTO lessonDTO = modelMapper.map(lesson, LessonDTO.class);
                        lessonDTO.setChapterDto(chapterDTO);
                        return lessonDTO;
                    })
                    .collect(Collectors.toList())
                    : null;
            chapterDTO.setLessonDtos(lessonDtos);

            return AppResult.successResult(chapterDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error updating chapter: " + e.getMessage()));
        }
    }
}
