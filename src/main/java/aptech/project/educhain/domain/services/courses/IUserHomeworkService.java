package aptech.project.educhain.domain.services.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.dtos.courses.UserAnswerDTO;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import aptech.project.educhain.domain.useCases.courses.UserHomework.AnswerQuestionUseCase.AnswerQuestionParams;
import aptech.project.educhain.domain.useCases.courses.UserHomework.GetUserHomeworkUseCase.GetUserHomeworkParams;
import aptech.project.educhain.domain.useCases.courses.UserHomework.SubmitHomeworkUseCase.SubmitHomeworkParams;

public interface IUserHomeworkService {
    AppResult<UserHomeworkDTO> getUserHomework(GetUserHomeworkParams params);

    AppResult<UserAnswerDTO> answerQuestion(AnswerQuestionParams params);

    AppResult<AwardDTO> submitHomework(SubmitHomeworkParams params);
}
