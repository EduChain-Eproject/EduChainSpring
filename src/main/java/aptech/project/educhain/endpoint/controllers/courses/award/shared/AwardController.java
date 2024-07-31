package aptech.project.educhain.endpoint.controllers.courses.award.shared;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

        if (result.isSuccess()) {
            return ResponseEntity.ok().body(result.getSuccess()); // TODO: map to res here
        }

        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

}
