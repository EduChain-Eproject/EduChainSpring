package aptech.project.educhain.domain.services.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.useCases.courses.UserAward.ApproveOrRejectAwardUseCase.ApproveOrRejectAwardParams;
import aptech.project.educhain.domain.useCases.courses.UserAward.GetUserAwardUseCase.GetUserAwardParams;
import aptech.project.educhain.domain.useCases.courses.UserAward.ReceiveAwardUseCase.ReceiveAwardParams;

public interface IAwardService {
    AppResult<AwardDTO> getAward(Integer awardId);

    AppResult<AwardDTO> getUserAward(GetUserAwardParams params);

    AppResult<AwardDTO> receiveAward(ReceiveAwardParams params);

    AppResult<AwardDTO> approveOrRejectAward(ApproveOrRejectAwardParams params);
}
