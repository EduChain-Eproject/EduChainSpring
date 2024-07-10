package aptech.project.educhain.common.usecase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import aptech.project.educhain.domain.useCases.account.user_interest.get_all_user_interest.GetUserInterestByUserIdParams;

import java.util.List;

public interface Usecase<SuccessType, NoParam> {
    public AppResult<SuccessType> execute(NoParam params);

}