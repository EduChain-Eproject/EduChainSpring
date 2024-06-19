package aptech.project.educhain.endpoint.controllers.courses.course.teacher;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.data.entities.courses.CourseStatus;
import aptech.project.educhain.data.serviceImpl.courses.CourseService;
import aptech.project.educhain.domain.useCases.courses.course.CreateCourseUsecase.CreateCourseParams;
import aptech.project.educhain.endpoint.requests.courses.course.teacher.CreateCourseRequest;
import aptech.project.educhain.endpoint.responses.courses.course.teacher.CreateCourseResponse;

@RestController
@RequestMapping("/TEACHER/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<?> createCourse(@RequestBody CreateCourseRequest request, BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            // ObjectError
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }

        int teacherId = 1;

        CreateCourseParams params = modelMapper.map(request, CreateCourseParams.class);
        params.setTeacherId(teacherId);
        params.setStatus(CourseStatus.UNDER_REVIEW);

        var course = courseService.createCourse(params);

        if (course.isSuccess()) {
            var res = modelMapper.map(course.getSuccess(), CreateCourseResponse.class);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(course.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }
}
