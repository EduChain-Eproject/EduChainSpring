package aptech.project.educhain.domain.useCases.personalization.user_profile.update_wallet_addres;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.domain.dtos.UserProfile.UserProfileDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateWalletAddressUsecase implements Usecase<UserProfileDTO, UpdateWalletAddressParams> {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthUserRepository authUserRepository;

    @Override
    @Transactional
    public AppResult<UserProfileDTO> execute(UpdateWalletAddressParams params) {
        try {
            User user = authUserRepository.findUserWithId(params.getUserId());

            user.setWalletAddress(params.getWalletAddress());

            User updatedUser = authUserRepository.save(user);

            UserProfileDTO userProfileDTO = modelMapper.map(updatedUser, UserProfileDTO.class);

            return AppResult.successResult(userProfileDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to update wallet address: " + e.getMessage()));
        }
    }

}
