package aptech.project.educhain.data.serviceImpl.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.services.courses.IAnswerService;
import aptech.project.educhain.domain.useCases.courses.Answers.GetAnswerByQuestionUseCase.GetAnswerByQuestionParam;
import aptech.project.educhain.domain.useCases.courses.Answers.GetAnswerUseCase.GetAnswerParam;
import aptech.project.educhain.domain.useCases.courses.Answers.UpdateAnswerUseCase.UpdateAnswerParam;

public class AnswerService implements IAnswerService {
    @Override
    public AppResult<AnswerDTO> getAnswer(GetAnswerParam param) {
        return null;
    }

    @Override
    public AppResult<AnswerDTO> getAnswerByQuestion(GetAnswerByQuestionParam param) {
        return null;
    }

    @Override
    public AppResult<AnswerDTO> updateAnswer(UpdateAnswerParam param) {
        return null;
    }
}
