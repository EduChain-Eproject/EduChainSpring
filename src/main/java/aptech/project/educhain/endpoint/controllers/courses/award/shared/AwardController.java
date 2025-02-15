package aptech.project.educhain.endpoint.controllers.courses.award.shared;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.endpoint.responses.courses.AwardResponse.AwardResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.serviceImpl.courses.AwardService;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "SharedStudentAward")
@RestController("SharedStudentAward")
@CrossOrigin
@RequestMapping("/SHARED/api/award")
public class AwardController {
    @Autowired
    AwardService AwardService;

    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "receive an award")
    @GetMapping("detail/{award_id}")
    public ResponseEntity<?> receive(@PathVariable Integer award_id) {
        AppResult<AwardDTO> result = AwardService.getAward(award_id);

        AwardResponse awardResponse = null;
        if (result.isSuccess()) {
            awardResponse = modelMapper.map(result.getSuccess(), AwardResponse.class);
            return ResponseEntity.ok().body(awardResponse); // TODO: map to res done
        }

        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()), HttpStatus.OK);
    }
}

