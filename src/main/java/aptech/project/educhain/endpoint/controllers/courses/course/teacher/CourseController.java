package aptech.project.educhain.endpoint.controllers.courses.course.teacher;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.courses.CourseStatus;
import aptech.project.educhain.data.serviceImpl.courses.CourseService;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.useCases.courses.course.CreateCourseUsecase.CreateCourseParams;
import aptech.project.educhain.domain.useCases.courses.course.GetCoursesByTeacherUsecase.GetCoursesByTeacherParams;
import aptech.project.educhain.domain.useCases.courses.course.UpdateCourseUsecase.UpdateCourseParams;
import aptech.project.educhain.endpoint.requests.courses.course.teacher.CourseListRequest;
import aptech.project.educhain.endpoint.requests.courses.course.teacher.CreateCourseRequest;
import aptech.project.educhain.endpoint.requests.courses.course.teacher.UpdateCourseRequest;
import aptech.project.educhain.endpoint.responses.courses.course.teacher.CreateCourseResponse;
import aptech.project.educhain.endpoint.responses.courses.course.teacher.GetCourseDetailResponse;
import aptech.project.educhain.endpoint.responses.courses.course.teacher.UpdateCourseResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/TEACHER/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("create")
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

    @PostMapping("/list")
    public ResponseEntity<?> getCoursesByTeacher(@Valid @RequestBody CourseListRequest request, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());
        }

        GetCoursesByTeacherParams params = modelMapper.map(request, GetCoursesByTeacherParams.class);
        AppResult<Page<CourseDTO>> appResult = courseService.getCoursesByTeacher(params);

        if (appResult.isSuccess()) {
            Page<CourseDTO> coursesPage = appResult.getSuccess();
            return ResponseEntity.ok(coursesPage);
        }
        return ResponseEntity.badRequest().body(appResult.getFailure().getMessage());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseDetail(@PathVariable("courseId") Integer courseId) {
        AppResult<CourseDTO> result = courseService.getCourseDetail(courseId);
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), GetCourseDetailResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable int courseId, @RequestBody UpdateCourseRequest request) {
        UpdateCourseParams params = modelMapper.map(request, UpdateCourseParams.class);
        params.setCourseId(courseId);

        AppResult<CourseDTO> result = courseService.updateCourse(params);
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), UpdateCourseResponse.class);
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @DeleteMapping("/deactivate/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable int courseId) {
        AppResult<CourseDTO> result = courseService.deleteCourse(courseId);
        if (result.isSuccess()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }
}
