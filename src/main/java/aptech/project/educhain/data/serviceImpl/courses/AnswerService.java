package aptech.project.educhain.data.serviceImpl.courses;

import java.util.List;

import org.springframework.stereotype.Service;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.services.courses.IAnswerService;
import aptech.project.educhain.domain.useCases.courses.Answer.GetAnswerByQuestionUseCase.GetAnswerByQuestionUseCase;
import aptech.project.educhain.domain.useCases.courses.Answer.GetAnswerUseCase.GetAnswerUseCase;
import aptech.project.educhain.domain.useCases.courses.Answer.UpdateAnswerUseCase.UpdateAnswerParam;
import aptech.project.educhain.domain.useCases.courses.Answer.UpdateAnswerUseCase.UpdateAnswerUseCase;

@Service
public class AnswerService implements IAnswerService {
    private final GetAnswerByQuestionUseCase getAnswerByQuestionUseCase;
    private final GetAnswerUseCase getAnswerUseCase;
    private final UpdateAnswerUseCase updateAnswerUseCase;

    public AnswerService(GetAnswerByQuestionUseCase getAnswerByQuestionUseCase, GetAnswerUseCase getAnswerUseCase,
            UpdateAnswerUseCase updateAnswerUseCase) {
        this.getAnswerByQuestionUseCase = getAnswerByQuestionUseCase;
        this.getAnswerUseCase = getAnswerUseCase;
        this.updateAnswerUseCase = updateAnswerUseCase;
    }

    @Override
    public AppResult<AnswerDTO> getAnswer(Integer id) {
        return getAnswerUseCase.execute(id);
    }

    @Override
    public AppResult<List<AnswerDTO>> getAnswerByQuestion(Integer id) {
        return getAnswerByQuestionUseCase.execute(id);
    }

    @Override
    public AppResult<AnswerDTO> updateAnswer(UpdateAnswerParam param) {
        return updateAnswerUseCase.execute(param);
    }
}
