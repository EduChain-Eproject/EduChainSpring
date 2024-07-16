package aptech.project.educhain.data.serviceImpl.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.services.courses.IAnswerService;
import aptech.project.educhain.domain.useCases.courses.Answers.GetAnswerByQuestionUseCase.GetAnswerByQuestionUseCase;
import aptech.project.educhain.domain.useCases.courses.Answers.GetAnswerUseCase.GetAnswerUseCase;
import aptech.project.educhain.domain.useCases.courses.Answers.UpdateAnswerUseCase.UpdateAnswerParam;
import aptech.project.educhain.domain.useCases.courses.Answers.UpdateAnswerUseCase.UpdateAnswerUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService implements IAnswerService {
    private final GetAnswerByQuestionUseCase getAnswerByQuestionUseCase;
    private final GetAnswerUseCase getAnswerUseCase;
    private final UpdateAnswerUseCase updateAnswerUseCase;

    public AnswerService(GetAnswerByQuestionUseCase getAnswerByQuestionUseCase, GetAnswerUseCase getAnswerUseCase, UpdateAnswerUseCase updateAnswerUseCase) {
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
