package aptech.project.educhain.domain.services.courses;

import java.util.List;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.useCases.courses.Answer.UpdateAnswerUseCase.UpdateAnswerParam;

public interface IAnswerService {
    public AppResult<AnswerDTO> getAnswer(Integer id);

    public AppResult<List<AnswerDTO>> getAnswerByQuestion(Integer id);

    public AppResult<AnswerDTO> updateAnswer(UpdateAnswerParam param);
}
