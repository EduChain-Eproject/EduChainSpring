package aptech.project.educhain.endpoint.controllers.courses.lesson.student;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.serviceImpl.courses.LessonService;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;
import aptech.project.educhain.endpoint.responses.courses.lesson.student.GetLessonDetailResponse;

@RestController("StudentLessonController")
@RequestMapping("/STUDENT/api/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/detail/{lessonId}")
    public ResponseEntity<?> getLessonDetail(@PathVariable("lessonId") Integer lessonId) {
        AppResult<LessonDTO> result = lessonService.getLessonDetail(lessonId);
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), GetLessonDetailResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }
}
