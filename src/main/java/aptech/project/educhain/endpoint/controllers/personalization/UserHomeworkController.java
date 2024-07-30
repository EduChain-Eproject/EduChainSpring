package aptech.project.educhain.endpoint.controllers.personalization;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import aptech.project.educhain.domain.services.accounts.IAuthService;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.services.personalization.UserProfileService;
import aptech.project.educhain.domain.useCases.personalization.user_homework.list_userhomework.ListUserHomeworkParams;
import aptech.project.educhain.endpoint.requests.personaliztion.user_homework.UserHomeworkRequest;
import aptech.project.educhain.endpoint.responses.common.UserHomeworkResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("STUDENT")
public class UserHomeworkController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserProfileService userProfileService;

    @PostMapping("/list-homework")
    public ResponseEntity<?> getAwardByUserIdAndAwardId(@RequestBody UserHomeworkRequest req) {
        ListUserHomeworkParams params = modelMapper.map(req,ListUserHomeworkParams.class);
        AppResult<Page<UserHomeworkDTO>> result = userProfileService.listHomeworkdByUserId(params);
        if (result.isSuccess()) {
            var res = result.getSuccess().map(userHomeworkDTO -> modelMapper.map(userHomeworkDTO, UserHomeworkResponse.class));
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

}
