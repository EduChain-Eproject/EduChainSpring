package aptech.project.educhain.domain.useCases.courses.lesson.GetLessonDetailUsecase;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Lesson;
import aptech.project.educhain.data.repositories.courses.LessonRepository;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;

@Component
public class GetLessonDetailUsecase implements Usecase<LessonDTO, Integer> {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<LessonDTO> execute(Integer lessonId) {
        try {
            Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
            if (!lessonOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Lesson not found with ID: " + lessonId));
            }

            Lesson lesson = lessonOptional.get();
            LessonDTO lessonDTO = modelMapper.map(lesson, LessonDTO.class);

            ChapterDTO chapterDTO = modelMapper.map(lesson.getChapter(), ChapterDTO.class);
            lessonDTO.setChapterDto(chapterDTO);

            return AppResult.successResult(lessonDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error retrieving lesson details: " + e.getMessage()));
        }
    }
}
