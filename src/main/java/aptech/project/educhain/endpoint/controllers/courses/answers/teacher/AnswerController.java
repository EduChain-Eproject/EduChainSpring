package aptech.project.educhain.endpoint.controllers.courses.answers.teacher;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.serviceImpl.courses.AnswerService;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.useCases.courses.Answers.UpdateAnswerUseCase.UpdateAnswerParam;
import aptech.project.educhain.endpoint.requests.answer.UpdateAnswerRequest;
import aptech.project.educhain.endpoint.responses.courses.answer.GetAnswerByQuestionResponse;
import aptech.project.educhain.endpoint.responses.courses.answer.GetAnswerResponse;
import aptech.project.educhain.endpoint.responses.courses.answer.UpdateAnswerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Answer")
@RestController
@CrossOrigin
@RequestMapping("/TEACHER/api/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "Get answer by question homework")
    @GetMapping("/question/{id}")
    public ResponseEntity<?> getAnswerByQuestion(@PathVariable Integer id) {
        AppResult<List<AnswerDTO>> result = answerService.getAnswerByQuestion(id);
        if (result.isSuccess()) {
            var res = result
                    .getSuccess()
                    .stream()
                    .map(dto -> modelMapper.map(dto, GetAnswerByQuestionResponse.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @Operation(summary = "Get 1 answer")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAnswer(@PathVariable Integer id) {
        AppResult<AnswerDTO> result = answerService.getAnswer(id);
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), GetAnswerResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @Operation(summary = "Update answer")
    @PutMapping("{id}")
    public ResponseEntity<?> updateAnswer(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateAnswerRequest request,
            BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<ObjectError> errorList = rs.getAllErrors();
            for (ObjectError err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
        UpdateAnswerParam params = modelMapper.map(request, UpdateAnswerParam.class);
        params.setId(id);

        var ans = answerService.updateAnswer(params);

        if (ans.isSuccess()) {
            var res = modelMapper.map(ans.getSuccess(), UpdateAnswerResponse.class);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(ans.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }
}
