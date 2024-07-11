package aptech.project.educhain.domain.services.personalization;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import aptech.project.educhain.domain.useCases.personalization.user_interest.add_to_user_interests.AddToUserInterestsParams;
import aptech.project.educhain.domain.useCases.personalization.user_interest.delete_from_user_interests.DeleteUserInterestsParams;
import aptech.project.educhain.domain.useCases.personalization.user_interest.get_all_user_interest.GetUserInterestByUserIdParams;
import org.springframework.data.domain.Page;

public interface UserInterestsService {
    AppResult<UserInterestsDTO> addUserInterest(AddToUserInterestsParams params);
    AppResult<Page<UserInterestsDTO>> getAlluserInterestByUserId(GetUserInterestByUserIdParams params);
    AppResult<Boolean> deleteUserInterest(DeleteUserInterestsParams params);
}
