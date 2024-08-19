package aptech.project.educhain.endpoint.controllers.courses.lesson.teacher;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.repositories.courses.LessonRepository;
import aptech.project.educhain.data.serviceImpl.common.UploadVideoServiceImpl;
import aptech.project.educhain.data.serviceImpl.courses.LessonService;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;
import aptech.project.educhain.domain.useCases.courses.lesson.CreateLessonUsecase.CreateLessonParams;
import aptech.project.educhain.domain.useCases.courses.lesson.GetLessonDetailUsecase.GetLessonDetailParams;
import aptech.project.educhain.domain.useCases.courses.lesson.UpdateLessonUsecase.UpdateLessonParams;
import aptech.project.educhain.endpoint.requests.courses.lesson.teacher.CreateLessonRequest;
import aptech.project.educhain.endpoint.requests.courses.lesson.teacher.UpdateLessonRequest;
import aptech.project.educhain.endpoint.responses.courses.lesson.teacher.CreateLessonResponse;
import aptech.project.educhain.endpoint.responses.courses.lesson.teacher.GetLessonDetailResponse;
import aptech.project.educhain.endpoint.responses.courses.lesson.teacher.UpdateLessonResponse;

@RestController
@RequestMapping("/TEACHER/api/lesson")
public class LessonController {
    @Value("${file.video.upload-dir}")
    private String uploadDir;
    @Autowired
    private UploadVideoServiceImpl uploadVideoService;
    @Autowired
    private LessonService lessonService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LessonRepository lessonRepository;

    @GetMapping("/detail/{lessonId}")
    public ResponseEntity<?> getLessonDetail(@PathVariable("lessonId") Integer lessonId) {
        AppResult<LessonDTO> result = lessonService.getLessonDetail(new GetLessonDetailParams(null, lessonId));
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), GetLessonDetailResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLesson(
            @Validated @ModelAttribute CreateLessonRequest request,
            BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(new ApiError(errors), HttpStatus.BAD_REQUEST);
        }
        CreateLessonParams params = modelMapper.map(request, CreateLessonParams.class);
        AppResult<LessonDTO> lesson = lessonService.createLesson(params);
        if (lesson.isSuccess()) {
            var res = modelMapper.map(lesson.getSuccess(), CreateLessonResponse.class);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ApiError(lesson.getFailure().getMessage()), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/{lessonId}")
    public ResponseEntity<?> updateLesson(@PathVariable("lessonId") Integer lessonId,
            @Validated @ModelAttribute UpdateLessonRequest request,
            BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(new ApiError(errors), HttpStatus.BAD_REQUEST);
        }
        UpdateLessonParams params = modelMapper.map(request, UpdateLessonParams.class);
        params.setId(lessonId);

        AppResult<LessonDTO> lesson = lessonService.updateLesson(params);

        if (lesson.isSuccess()) {
            var res = modelMapper.map(lesson.getSuccess(), UpdateLessonResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return new ResponseEntity<>(new ApiError(lesson.getFailure().getMessage()), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable("lessonId") Integer lessonId) {
        var result = lessonService.deleteLesson(lessonId);
        if (result.isSuccess()) {
            return ResponseEntity.ok().body(lessonId);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }
}
