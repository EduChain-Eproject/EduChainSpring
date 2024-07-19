package aptech.project.educhain.data.serviceImpl.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.UserAnswerDTO;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import aptech.project.educhain.domain.services.courses.IUserHomeworkService;
import aptech.project.educhain.domain.useCases.courses.UserHomework.AnswerQuestionUseCase.AnswerQuestionParams;
import aptech.project.educhain.domain.useCases.courses.UserHomework.AnswerQuestionUseCase.AnswerQuestionUseCase;
import aptech.project.educhain.domain.useCases.courses.UserHomework.GetUserHomeworkUseCase.GetUserHomeworkParams;
import aptech.project.educhain.domain.useCases.courses.UserHomework.GetUserHomeworkUseCase.GetUserHomeworkUseCase;
import aptech.project.educhain.domain.useCases.courses.UserHomework.SubmitHomeworkUseCase.SubmitHomeworkParams;
import aptech.project.educhain.domain.useCases.courses.UserHomework.SubmitHomeworkUseCase.SubmitHomeworkUseCase;
import aptech.project.educhain.endpoint.responses.courses.homework.SubmitHomeworkResponse;

@Service
public class UserHomeworkService implements IUserHomeworkService {

    @Autowired
    GetUserHomeworkUseCase getUserHomeworkUseCase;

    @Autowired
    SubmitHomeworkUseCase submitHomeworkUseCase;

    @Autowired
    AnswerQuestionUseCase answerQuestionUseCase;

    @Override
    public AppResult<UserHomeworkDTO> getUserHomework(GetUserHomeworkParams params) {
        return getUserHomeworkUseCase.execute(params);
    }

    @Override
    public AppResult<UserAnswerDTO> answerQuestion(AnswerQuestionParams params) {
        return answerQuestionUseCase.execute(params);
    }

    @Override
    public AppResult<SubmitHomeworkResponse> submitHomework(SubmitHomeworkParams params) {
        return submitHomeworkUseCase.execute(params);
    }

}
