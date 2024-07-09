package aptech.project.educhain.endpoint.controllers.courses.questions;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.courses.CourseStatus;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.serviceImpl.courses.HomeworkService;
import aptech.project.educhain.data.serviceImpl.courses.QuestionService;
import aptech.project.educhain.domain.dtos.blogs.BlogDTO;
import aptech.project.educhain.domain.dtos.courses.*;
import aptech.project.educhain.domain.useCases.courses.Homework.CreateHomeworkUseCase.CreateHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.DeleteHomeworkUseCase.DeleteHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.GetHomeworkdUseCase.GetHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.UpdateHomeworkUseCase.UpdateHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Question.CreateQuestionUseCase.CreateQuestionParam;
import aptech.project.educhain.domain.useCases.courses.Question.UpdateQuestionUseCase.UpdateQuestionParam;
import aptech.project.educhain.domain.useCases.courses.chapter.GetChapterDetailUsecase.GetChapterDetailParams;
import aptech.project.educhain.domain.useCases.courses.course.CreateCourseUsecase.CreateCourseParams;
import aptech.project.educhain.endpoint.requests.Homework.CreateHomeworkRequest;
import aptech.project.educhain.endpoint.requests.Homework.UpdateHomeworkRequest;
import aptech.project.educhain.endpoint.requests.courses.course.teacher.CreateCourseRequest;
import aptech.project.educhain.endpoint.requests.question.CreateQuestionRequest;
import aptech.project.educhain.endpoint.requests.question.UpdateQuestionRequest;
import aptech.project.educhain.endpoint.responses.courses.category.teacher.GetListCategoryResponse;
import aptech.project.educhain.endpoint.responses.courses.chapter.teacher.CreateChapterResponse;
import aptech.project.educhain.endpoint.responses.courses.chapter.teacher.GetChapterDetailResponse;
import aptech.project.educhain.endpoint.responses.courses.course.teacher.CreateCourseResponse;
import aptech.project.educhain.endpoint.responses.courses.homework.CreateHomeworkResponse;
import aptech.project.educhain.endpoint.responses.courses.homework.GetHomeworkResponse;
import aptech.project.educhain.endpoint.responses.courses.homework.GetListHomeworkResponse;
import aptech.project.educhain.endpoint.responses.courses.homework.UpdateHomeworkResponse;
import aptech.project.educhain.endpoint.responses.courses.question.CreateQuestionResponse;
import aptech.project.educhain.endpoint.responses.courses.question.GetListQuestionsByHomeworkResponse;
import aptech.project.educhain.endpoint.responses.courses.question.GetQuestionResponse;
import aptech.project.educhain.endpoint.responses.courses.question.UpdateQuestionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "question")
@RestController
@CrossOrigin
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    ModelMapper modelMapper;


    @Operation(summary = "Get all questions")
    @GetMapping("/homework/{id}")
    public ResponseEntity<?> getQuestionsByHomework(@PathVariable Integer id) {
        AppResult<List<QuestionDTO>> result = questionService.getQuestionByHomework(id);
        if (result.isSuccess()) {
            var res = result
                    .getSuccess()
                    .stream()
                    .map(dto -> modelMapper.map(dto, GetListQuestionsByHomeworkResponse.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @Operation(summary = "Get 1 question")
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable Integer id) {
        AppResult<QuestionDTO> result = questionService.getQuestion(id);
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), GetQuestionResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @Operation(summary = "Create question")
    @PostMapping()
    public ResponseEntity<?> createQuestion(@Valid @RequestBody CreateQuestionRequest request, BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }

        CreateQuestionParam params = modelMapper.map(request, CreateQuestionParam.class);

        var question = questionService.createQuestion(params);

        if (question.isSuccess()) {
            var res = modelMapper.map(question.getSuccess(), CreateQuestionResponse.class);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(question.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Update question")
    @PutMapping("{id}")
    public ResponseEntity<?> updateQuestion(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateQuestionRequest request,
            BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<ObjectError> errorList = rs.getAllErrors();
            for (ObjectError err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
        UpdateQuestionParam params = modelMapper.map(request, UpdateQuestionParam.class);
        params.setId(id);

        AppResult<QuestionDTO> questionDTO = questionService.updateQuestion(params);

        if (questionDTO.isSuccess()) {
            var res = modelMapper.map(questionDTO.getSuccess(), UpdateQuestionResponse.class);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(questionDTO.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Delete question")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Integer id) {
        var result = questionService.deleteQuestion(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok().body("Delete question with id: + " + id);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }
}
