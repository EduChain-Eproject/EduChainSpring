package aptech.project.educhain.domain.useCases.admin.block_or_unblock;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.domain.useCases.personalization.user_interest.delete_from_user_interests.DeleteUserInterestsParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component

public class BlockOrUnBlockUseCase implements Usecase<Boolean, BlockOrUnBlockParams> {
    @Autowired
    AuthUserRepository authUserRepository;
    @Override
    public AppResult<Boolean> execute(BlockOrUnBlockParams params) {
        try {
            Optional<User> optionalUser = authUserRepository.findById(params.getUserId());
            if (!optionalUser.isPresent()) {
                return AppResult.failureResult(new Failure("User not found"));
            }

            User user = optionalUser.get();
            if(user.getRole().name().equals("ADMIN")){
                return AppResult.failureResult(new Failure("cant change admin role"));
            }
            if(params.getBlockValue()) {
                params.setBlockValue(false);
            }
            else {
                params.setBlockValue(true);
            }
            user.setIsActive(params.getBlockValue());
            User updatedUser = authUserRepository.save(user);

            return AppResult.successResult(true);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to update user: " + e.getMessage()));
        }
    }
}
