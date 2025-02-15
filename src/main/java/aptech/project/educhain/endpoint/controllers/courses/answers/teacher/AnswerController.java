package aptech.project.educhain.endpoint.controllers.courses.answers.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aptech.project.educhain.common.result.ApiError;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.data.serviceImpl.courses.AnswerService;
import aptech.project.educhain.domain.useCases.courses.Answer.UpdateAnswerUseCase.UpdateAnswerParam;
import aptech.project.educhain.endpoint.requests.answer.UpdateAnswerRequest;
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

    @Operation(summary = "Update answer")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAnswer(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateAnswerRequest request,
            BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(new ApiError(errors), HttpStatus.BAD_REQUEST);
        }
        UpdateAnswerParam params = modelMapper.map(request, UpdateAnswerParam.class);
        params.setId(id);

        var ans = answerService.updateAnswer(params);

        if (ans.isSuccess()) {
            var res = modelMapper.map(ans.getSuccess(), UpdateAnswerResponse.class);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ApiError(ans.getFailure().getMessage()), HttpStatus.BAD_REQUEST);
    }
}
