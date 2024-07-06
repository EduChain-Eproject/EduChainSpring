package aptech.project.educhain.domain.services.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.useCases.courses.Answers.GetAnswerByQuestionUseCase.GetAnswerByQuestionParam;
import aptech.project.educhain.domain.useCases.courses.Answers.GetAnswerUseCase.GetAnswerParam;
import aptech.project.educhain.domain.useCases.courses.Answers.UpdateAnswerUseCase.UpdateAnswerParam;

public interface IAnswerService {
    public AppResult<AnswerDTO> getAnswer(GetAnswerParam param);
    public AppResult<AnswerDTO> getAnswerByQuestion(GetAnswerByQuestionParam param);
    public AppResult<AnswerDTO> updateAnswer(UpdateAnswerParam param);
}
