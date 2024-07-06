package aptech.project.educhain.endpoint.controllers.courses.homeworks;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.courses.CourseStatus;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.serviceImpl.courses.HomeworkService;
import aptech.project.educhain.domain.dtos.blogs.BlogDTO;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.useCases.courses.Homework.CreateHomeworkUseCase.CreateHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.DeleteHomeworkUseCase.DeleteHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.GetHomeworkdUseCase.GetHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.UpdateHomeworkUseCase.UpdateHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.course.CreateCourseUsecase.CreateCourseParams;
import aptech.project.educhain.endpoint.requests.Homework.CreateHomeworkRequest;
import aptech.project.educhain.endpoint.requests.Homework.UpdateHomeworkRequest;
import aptech.project.educhain.endpoint.requests.courses.course.teacher.CreateCourseRequest;
import aptech.project.educhain.endpoint.responses.courses.category.teacher.GetListCategoryResponse;
import aptech.project.educhain.endpoint.responses.courses.course.teacher.CreateCourseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Homework")
@RestController
@CrossOrigin
@RequestMapping("/api/homework")
public class HomeworkController {
    @Autowired
    HomeworkService homeworkService;

    @Autowired
    ModelMapper modelMapper;


    @Operation(summary = "Get all homework")
    @GetMapping("")
    public ResponseEntity<?> getHomeworks() {
        AppResult<List<HomeworkDTO>> result = homeworkService.getHomeworks(NoParam.get());
        if (result.isSuccess()) {
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @Operation(summary = "Get 1 homework")
    @GetMapping("/{id}")
    public ResponseEntity<?> getHomework(@PathVariable Integer id) {
        GetHomeworkParam param = modelMapper.map(id, GetHomeworkParam.class);
        AppResult<HomeworkDTO> result = homeworkService.getHomework(param);
        if (result.isSuccess()) {
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @Operation(summary = "Create homework")
    @PostMapping("create")
    public ResponseEntity<?> createHomework(@RequestBody CreateHomeworkRequest request, BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }


        int userId = 1;
        int lessonId = 1;

        CreateHomeworkParam params = modelMapper.map(request, CreateHomeworkParam.class);
        params.setUserId(userId);
        params.setLessonId(lessonId);

        var homework = homeworkService.createHomework(params);

        if (homework.isSuccess()) {
            return new ResponseEntity<>(homework, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(homework.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Update homework")
    @PostMapping("update")
    public ResponseEntity<?> updateHomework(@PathVariable Integer id, @RequestBody UpdateHomeworkRequest request, BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
        UpdateHomeworkParam params = modelMapper.map(request, UpdateHomeworkParam.class);

        var homework = homeworkService.updateHomework(id, params);

        if (homework.isSuccess()) {
            return new ResponseEntity<>(homework, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(homework.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Delete homework")
    @DeleteMapping("/{id}")
    public AppResult<Boolean> delete(@PathVariable Integer id) {
        DeleteHomeworkParam param = modelMapper.map(id, DeleteHomeworkParam.class);
        return homeworkService.deleteHomework(param);
    }
}
