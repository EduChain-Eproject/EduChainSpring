package aptech.project.educhain.endpoint.controllers.courses.award.teacher;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.endpoint.responses.courses.AwardResponse.AwardResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.serviceImpl.courses.AwardService;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.useCases.courses.UserAward.ApproveOrRejectAwardUseCase.ApproveOrRejectAwardParams;
import aptech.project.educhain.endpoint.requests.award.ApproveRejectAwardReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "TeacherAward")
@RestController("TeacherAward")
@CrossOrigin
@RequestMapping("/TEACHER/api/award")
public class AwardController {
    @Autowired
    AwardService AwardService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IJwtService iJwtService;

    @Operation(summary = "approve/reject an award")
    @PostMapping("approve_or_reject/{award_id}")
    public ResponseEntity<?> approveOrReject(
            @PathVariable Integer award_id,
            @RequestBody ApproveRejectAwardReq bodyReq,
            HttpServletRequest request) {
        var user = iJwtService.getUserByHeaderToken(request.getHeader("Authorization"));

        AppResult<AwardDTO> result = AwardService.approveOrRejectAward(
                new ApproveOrRejectAwardParams(award_id, user.getId(), bodyReq.getUpdatingAwardStatus()));

        if (result.isSuccess()) {
            AwardResponse awardResponse = modelMapper.map(result.getSuccess(),AwardResponse.class);
            return ResponseEntity.ok().body(awardResponse); // TODO: map to res done
        }
        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()), HttpStatus.OK);
    }
}
