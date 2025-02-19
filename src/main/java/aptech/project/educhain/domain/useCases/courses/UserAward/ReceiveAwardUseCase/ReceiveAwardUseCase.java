package aptech.project.educhain.domain.useCases.courses.UserAward.ReceiveAwardUseCase;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Award;
import aptech.project.educhain.data.entities.courses.AwardStatus;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.AwardRepository;
import aptech.project.educhain.data.serviceImpl.web3.Web3Service;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;

@Component
public class ReceiveAwardUseCase implements Usecase<AwardDTO, ReceiveAwardParams> {

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    Web3Service web3Service;

    @Override
    public AppResult<AwardDTO> execute(ReceiveAwardParams params) {
        try {
            Optional<Award> awardOptional = awardRepository.findByUserIdAndAwardId(params.getUserId(),
                    params.getAwardId());
            if (!awardOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Award not found"));
            }

            Award award = awardOptional.get();

            if (!award.getStatus().equals(AwardStatus.APPROVED)) {
                return AppResult.failureResult(new Failure("Award is not approved"));
            } else if (award.getStatus().equals(AwardStatus.RECEIVED)) {
                return AppResult.failureResult(new Failure("Award is already received"));
            } else if (award.getStatus().equals(AwardStatus.REJECTED)) {
                return AppResult.failureResult(new Failure("Award is rejected"));
            }

            var user = authUserRepository.findUserWithId(params.getUserId());
            if (user.getWalletAddress() == null) {
                return AppResult.failureResult(new Failure("User has no wallet address"));
            } else {
                if (award.getTokenAmount() == null) {
                    return AppResult.failureResult(new Failure("Token amount is not set"));
                }
                AppResult<TransactionReceipt> result = web3Service.awardForHomework(user.getWalletAddress(),
                        award.getTokenAmount());

                if (result.isFailure()) {
                    return AppResult.failureResult(
                            new Failure("Failed to receive an award: " + result.getFailure().getMessage()));

                } else {
                    award.setTransactionHash(result.getSuccess().getTransactionHash());
                }

            }

            award.setStatus(AwardStatus.RECEIVED);

            awardRepository.save(award);

            return AppResult.successResult(modelMapper.map(award, AwardDTO.class));

        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to receive an award: " + e.getMessage()));
        }
    }
}
