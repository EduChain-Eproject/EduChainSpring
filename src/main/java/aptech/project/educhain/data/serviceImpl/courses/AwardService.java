package aptech.project.educhain.data.serviceImpl.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.services.courses.IAwardService;
import aptech.project.educhain.domain.useCases.courses.UserAward.ApproveOrRejectAwardUseCase.ApproveOrRejectAwardParams;
import aptech.project.educhain.domain.useCases.courses.UserAward.ApproveOrRejectAwardUseCase.ApproveOrRejectAwardUseCase;
import aptech.project.educhain.domain.useCases.courses.UserAward.GetUserAwardUseCase.GetUserAwardParams;
import aptech.project.educhain.domain.useCases.courses.UserAward.GetUserAwardUseCase.GetUserAwardUseCase;
import aptech.project.educhain.domain.useCases.courses.UserAward.ReceiveAwardUseCase.ReceiveAwardParams;
import aptech.project.educhain.domain.useCases.courses.UserAward.ReceiveAwardUseCase.ReceiveAwardUseCase;

@Service
public class AwardService implements IAwardService {

    @Autowired
    GetUserAwardUseCase getUserAwardUseCase;

    @Autowired
    ReceiveAwardUseCase receiveAwardUseCase;

    @Autowired
    ApproveOrRejectAwardUseCase approveOrRejectAwardUseCase;

    @Override
    public AppResult<AwardDTO> getUserAward(GetUserAwardParams params) {
        return getUserAwardUseCase.execute(params);
    }

    @Override
    public AppResult<AwardDTO> receiveAward(ReceiveAwardParams params) {
        return receiveAwardUseCase.execute(params);
    }

    @Override
    public AppResult<AwardDTO> approveOrRejectAward(ApproveOrRejectAwardParams params) {
        return approveOrRejectAwardUseCase.execute(params);
    }

}
