package aptech.project.educhain.endpoint.controllers.courses.questions.teacher;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.serviceImpl.courses.QuestionService;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import aptech.project.educhain.domain.useCases.courses.Question.CreateQuestionUseCase.CreateQuestionParam;
import aptech.project.educhain.domain.useCases.courses.Question.UpdateQuestionUseCase.UpdateQuestionParam;
import aptech.project.educhain.endpoint.requests.question.CreateQuestionRequest;
import aptech.project.educhain.endpoint.requests.question.UpdateQuestionRequest;
import aptech.project.educhain.endpoint.responses.courses.question.CreateQuestionResponse;
import aptech.project.educhain.endpoint.responses.courses.question.GetQuestionResponse;
import aptech.project.educhain.endpoint.responses.courses.question.UpdateQuestionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "question")
@RestController
@CrossOrigin
@RequestMapping("/TEACHER/api/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "Get 1 question")
    @GetMapping("detail/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable Integer id) {
        AppResult<QuestionDTO> result = questionService.getQuestion(id);
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), GetQuestionResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @Operation(summary = "Create question")
    @PostMapping("create")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody CreateQuestionRequest request, BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());// TODO
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
    @PutMapping("update/{id}")
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
            return ResponseEntity.badRequest().body(errors.toString());// TODO
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
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Integer id) {
        var result = questionService.deleteQuestion(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok().body(id);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }
}
