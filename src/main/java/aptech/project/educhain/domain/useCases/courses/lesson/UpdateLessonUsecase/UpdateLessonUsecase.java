package aptech.project.educhain.domain.useCases.courses.lesson.UpdateLessonUsecase;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import aptech.project.educhain.data.serviceImpl.common.UploadVideoServiceImpl;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Lesson;
import aptech.project.educhain.data.repositories.courses.LessonRepository;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;

@Component
public class UpdateLessonUsecase implements Usecase<LessonDTO, UpdateLessonParams> {
    @Value("${file.video.upload-dir}")
    private String uploadDir;
    @Autowired
    private UploadVideoServiceImpl uploadVideoService;
    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public AppResult<LessonDTO> execute(UpdateLessonParams params) {
        try {
         Optional<Lesson> lessonOptional = lessonRepository.findById(params.getId());

            if (lessonOptional == null) {
                return AppResult.failureResult(new Failure("Lesson not found with ID: " + params.getId()));
            }

          Lesson lesson = lessonOptional.get();
            lesson.setLessonTitle(params.getLessonTitle());
            lesson.setDescription(params.getDescription());
            lesson.setVideoTitle(params.getVideoTitle());

            //
            String fileName = uploadVideoService.uploadVideo(params.getVideoFile());
            String oldVideo = lesson.getVideoURL();
            String video = fileName != null ? fileName : oldVideo;
            if (fileName != null) {
                Path path = Paths.get(uploadDir);
                if(video == null){
                    Files.deleteIfExists(path.resolve(oldVideo));
                }
            }
            lesson.setVideoURL(video);
            Lesson updatedLesson = lessonRepository.saveAndFlush(lesson);
            LessonDTO lessonDTO = modelMapper.map(updatedLesson, LessonDTO.class);

            ChapterDTO chapterDTO = modelMapper.map(updatedLesson.getChapter(), ChapterDTO.class);
            lessonDTO.setChapterDto(chapterDTO);

            return AppResult.successResult(lessonDTO);
        }catch (Exception e) {
            return AppResult.failureResult(new Failure("Error updating lesson: " + e.getMessage()));

        }
    }
}
