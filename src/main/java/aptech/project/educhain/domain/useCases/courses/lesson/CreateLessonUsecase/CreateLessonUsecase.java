package aptech.project.educhain.domain.useCases.courses.lesson.CreateLessonUsecase;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Chapter;
import aptech.project.educhain.data.entities.courses.Lesson;
import aptech.project.educhain.data.repositories.courses.ChapterRepository;
import aptech.project.educhain.data.repositories.courses.LessonRepository;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;

@Component
public class CreateLessonUsecase implements Usecase<LessonDTO, CreateLessonParams> {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<LessonDTO> execute(CreateLessonParams params) {
        try {
            Optional<Chapter> chapterOptional = chapterRepository.findById(params.getChapterId());
            if (!chapterOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Chapter not found with ID: " + params.getChapterId()));
            }

            Lesson lesson = modelMapper.map(params, Lesson.class);
            lesson.setChapter(chapterOptional.get());

            Lesson savedLesson = lessonRepository.saveAndFlush(lesson);
            LessonDTO lessonDTO = modelMapper.map(savedLesson, LessonDTO.class);

            ChapterDTO chapterDTO = modelMapper.map(savedLesson.getChapter(), ChapterDTO.class);
            lessonDTO.setChapterDto(chapterDTO);

            return AppResult.successResult(lessonDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error creating lesson: " + e.getMessage()));
        }
    }
}
