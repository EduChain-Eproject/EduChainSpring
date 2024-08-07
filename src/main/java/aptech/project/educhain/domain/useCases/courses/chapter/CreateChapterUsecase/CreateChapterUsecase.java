package aptech.project.educhain.domain.useCases.courses.chapter.CreateChapterUsecase;

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
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;

@Component
public class CreateChapterUsecase implements Usecase<ChapterDTO, CreateChapterParams> {

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<ChapterDTO> execute(CreateChapterParams params) {
        try {
            Optional<Course> courseOptional = courseRepository.findById(params.getCourseId());
            if (!courseOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Course not found with ID: " + params.getCourseId()));
            }

            Chapter chapter = modelMapper.map(params, Chapter.class);
            chapter.setCourse(courseOptional.get());

            Chapter savedChapter = chapterRepository.saveAndFlush(chapter);
            ChapterDTO chapterDTO = modelMapper.map(savedChapter, ChapterDTO.class);
            chapterDTO.setCourseDto(modelMapper.map(savedChapter.getCourse(), CourseDTO.class));

            var listLessons = savedChapter.getLessons();
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
            return AppResult.failureResult(new Failure("Error creating chapter: " + e.getMessage()));
        }
    }
}
