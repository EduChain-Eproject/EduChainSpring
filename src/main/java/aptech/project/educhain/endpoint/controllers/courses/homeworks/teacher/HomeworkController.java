package aptech.project.educhain.endpoint.controllers.courses.homeworks.teacher;

import java.util.List;
import java.util.stream.Collectors;

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
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.data.serviceImpl.courses.HomeworkService;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.useCases.courses.Homework.CreateHomeworkUseCase.CreateHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.DeleteHomeworkUseCase.DeleteHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.GetHomeworkdUseCase.GetHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.UpdateHomeworkUseCase.UpdateHomeworkParam;
import aptech.project.educhain.endpoint.requests.Homework.CreateHomeworkRequest;
import aptech.project.educhain.endpoint.requests.Homework.UpdateHomeworkRequest;
import aptech.project.educhain.endpoint.responses.courses.homework.CreateHomeworkResponse;
import aptech.project.educhain.endpoint.responses.courses.homework.GetHomeworkResponse;
import aptech.project.educhain.endpoint.responses.courses.homework.GetListHomeworkResponse;
import aptech.project.educhain.endpoint.responses.courses.homework.UpdateHomeworkResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Homework")
@RestController
@CrossOrigin
@RequestMapping("/TEACHER/api/homework")
public class HomeworkController {
    @Autowired
    HomeworkService homeworkService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IJwtService iJwtService;

    @Operation(summary = "Get all homework")
    @GetMapping("list")
    public ResponseEntity<?> getHomeworks() {
        AppResult<List<HomeworkDTO>> result = homeworkService.getHomeworks(NoParam.get());
        if (result.isSuccess()) {
            var res = result
                    .getSuccess()
                    .stream()
                    .map(dto -> modelMapper.map(dto, GetListHomeworkResponse.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @Operation(summary = "Get 1 homework")
    @GetMapping("detail/{homework_id}")
    public ResponseEntity<?> getHomework(@PathVariable Integer homework_id) {
        AppResult<HomeworkDTO> result = homeworkService.getHomework(new GetHomeworkParam(homework_id));
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), GetHomeworkResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @Operation(summary = "Create homework")
    @PostMapping("create")
    public ResponseEntity<?> createHomework(@Valid @RequestBody CreateHomeworkRequest request, BindingResult rs,
            HttpServletRequest httpServletRequest) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }

        var createtorId = iJwtService.getUserByHeaderToken(httpServletRequest.getHeader("Authorization")).getId();
        CreateHomeworkParam params = modelMapper.map(request, CreateHomeworkParam.class);
        params.setUserId(createtorId);

        var homework = homeworkService.createHomework(params);
        if (homework.isSuccess()) {
            var res = modelMapper.map(homework.getSuccess(), CreateHomeworkResponse.class);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(homework.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Update homework")
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateHomework(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateHomeworkRequest request,
            BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<ObjectError> errorList = rs.getAllErrors();
            for (ObjectError err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
        UpdateHomeworkParam params = modelMapper.map(request, UpdateHomeworkParam.class);
        params.setId(id);

        var homework = homeworkService.updateHomework(params);

        if (homework.isSuccess()) {
            var res = modelMapper.map(homework.getSuccess(), UpdateHomeworkResponse.class);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(homework.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Delete homework")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteChapter(@PathVariable Integer id) {
        var result = homeworkService.deleteHomework(new DeleteHomeworkParam(id));
        if (result.isSuccess()) {
            return ResponseEntity.ok().body(id);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }
}
