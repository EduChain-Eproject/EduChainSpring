package aptech.project.educhain.domain.services.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.useCases.courses.Answers.UpdateAnswerUseCase.UpdateAnswerParam;

import java.util.List;

public interface IAnswerService {
    public AppResult<AnswerDTO> getAnswer(Integer id);
    public AppResult<List<AnswerDTO>> getAnswerByQuestion(Integer id);
    public AppResult<AnswerDTO> updateAnswer(UpdateAnswerParam param);
}
