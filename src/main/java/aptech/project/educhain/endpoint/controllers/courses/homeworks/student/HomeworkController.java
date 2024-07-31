package aptech.project.educhain.endpoint.controllers.courses.homeworks.student;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.serviceImpl.courses.AwardService;
import aptech.project.educhain.data.serviceImpl.courses.HomeworkService;
import aptech.project.educhain.data.serviceImpl.courses.UserHomeworkService;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.UserAnswerDTO;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.useCases.courses.Homework.GetHomeworkdUseCase.GetHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.UserAward.GetUserAwardUseCase.GetUserAwardParams;
import aptech.project.educhain.domain.useCases.courses.UserHomework.AnswerQuestionUseCase.AnswerQuestionParams;
import aptech.project.educhain.domain.useCases.courses.UserHomework.GetUserHomeworkUseCase.GetUserHomeworkParams;
import aptech.project.educhain.domain.useCases.courses.UserHomework.SubmitHomeworkUseCase.SubmitHomeworkParams;
import aptech.project.educhain.endpoint.requests.Homework.AnswerAQuestionReq;
import aptech.project.educhain.endpoint.responses.courses.homework.GetHomeworkAndUserHomeworkResponse;
import aptech.project.educhain.endpoint.responses.courses.homework.SubmitHomeworkResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "StudentHomework")
@RestController("StudentHomework")
@CrossOrigin
@RequestMapping("/STUDENT/api/homework")
public class HomeworkController {
    @Autowired
    HomeworkService homeworkService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IJwtService iJwtService;

    @Autowired
    UserHomeworkService userHomeworkService;

    @Autowired
    AwardService awardService;

    @Operation(summary = "Get 1 homework")
    @GetMapping("detail/{homework_id}")
    public ResponseEntity<?> getHomework(@PathVariable Integer homework_id, HttpServletRequest request) {
        var user = iJwtService.getUserByHeaderToken(request.getHeader("Authorization"));

        AppResult<HomeworkDTO> result1 = homeworkService.getHomework(new GetHomeworkParam(homework_id));

        if (result1.isSuccess()) {
            var homework = result1.getSuccess();

            AppResult<UserHomeworkDTO> result2 = userHomeworkService
                    .getUserHomework(new GetUserHomeworkParams(user.getId(), homework_id));

            if (result2.isSuccess()) {

                var userHomework = result2.getSuccess();

                if (userHomework.getUserAnswerDtos() != null && userHomework.getUserAnswerDtos().size() > 0) {
                    homework.mergeUserAnswersToQuestions(userHomework.getUserAnswerDtos());
                }

                AppResult<AwardDTO> result3 = awardService
                        .getUserAward(new GetUserAwardParams(user.getId(), homework_id));

                var res = new GetHomeworkAndUserHomeworkResponse(
                        homework,
                        userHomework,
                        result3.isSuccess() ? result3.getSuccess() : null);

                return ResponseEntity.ok().body(res);
            } else {
                return ResponseEntity.badRequest().body(result2.getFailure().getMessage());
            }
        }

        return ResponseEntity.badRequest().body(result1.getFailure().getMessage());
    }

    @Operation(summary = "answer a question of a homework")
    @PostMapping("answer/{homework_id}")
    public ResponseEntity<?> answerAQuestion(
            @PathVariable Integer homework_id,
            @Valid @RequestBody AnswerAQuestionReq bodyReq,
            BindingResult rs,
            HttpServletRequest request) {
        var user = iJwtService.getUserByHeaderToken(request.getHeader("Authorization"));

        AppResult<UserAnswerDTO> result = userHomeworkService.answerQuestion(
                new AnswerQuestionParams(user.getId(), homework_id, bodyReq.getQuestionId(), bodyReq.getAnswerId()));

        if (result.isSuccess()) {
            return ResponseEntity.ok().body(result.getSuccess()); // TODO: map to res here
        }

        return ResponseEntity.badRequest().body(result.getFailure().getMessage()); // TODO
    }

    @Operation(summary = "submit a homework")
    @PostMapping("submit/{homework_id}")
    public ResponseEntity<?> submit(@PathVariable Integer homework_id, HttpServletRequest request) {
        var user = iJwtService.getUserByHeaderToken(request.getHeader("Authorization"));

        AppResult<SubmitHomeworkResponse> result = userHomeworkService.submitHomework(
                new SubmitHomeworkParams(user.getId(), homework_id));

        if (result.isSuccess()) {
            return ResponseEntity.ok().body(result.getSuccess()); // TODO: map to res here
        }

        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

}
