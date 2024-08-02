package aptech.project.educhain.domain.useCases.courses.lesson.UpdateLessonUsecase;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Lesson;
import aptech.project.educhain.data.repositories.courses.ChapterRepository;
import aptech.project.educhain.data.repositories.courses.LessonRepository;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;

@Component
public class UpdateLessonUsecase implements Usecase<LessonDTO, UpdateLessonParams> {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<LessonDTO> execute(UpdateLessonParams params) {
        try {
            Optional<Lesson> lessonOptional = lessonRepository.findById(params.getId());
            if (!lessonOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Lesson not found with ID: " + params.getId()));
            }

            Lesson lesson = lessonOptional.get();

            lesson.setLessonTitle(params.getLessonTitle());
            lesson.setDescription(params.getDescription());
            lesson.setVideoTitle(params.getVideoTitle());
            lesson.setVideoURL(params.getVideoURL());

            var newChapter = chapterRepository.findById(params.getChapterId());
            if (newChapter.isEmpty()) {
                return AppResult
                        .failureResult(new Failure("Error updating lesson: chapter not found" ));
            }
            lesson.setChapter(newChapter.get());

            Lesson updatedLesson = lessonRepository.saveAndFlush(lesson);
            LessonDTO lessonDTO = modelMapper.map(updatedLesson, LessonDTO.class);

            ChapterDTO chapterDTO = modelMapper.map(updatedLesson.getChapter(), ChapterDTO.class);
            lessonDTO.setChapterDto(chapterDTO);

            return AppResult.successResult(lessonDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error updating lesson: " + e.getMessage()));
        }
    }
}
