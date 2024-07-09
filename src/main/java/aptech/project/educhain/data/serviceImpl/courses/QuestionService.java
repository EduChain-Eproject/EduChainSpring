package aptech.project.educhain.data.serviceImpl.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import aptech.project.educhain.domain.services.courses.IQuestionService;
import aptech.project.educhain.domain.useCases.courses.Question.CreateQuestionUseCase.CreateQuestionParam;
import aptech.project.educhain.domain.useCases.courses.Question.CreateQuestionUseCase.CreateQuestionUseCase;
import aptech.project.educhain.domain.useCases.courses.Question.DeleteQuestionUseCase.DeleteQuestionUseCase;
import aptech.project.educhain.domain.useCases.courses.Question.GetQuestionUseCase.GetQuestionUseCase;
import aptech.project.educhain.domain.useCases.courses.Question.GetQuestionsByHomeworkUseCase.GetQuestionsByHomeworkUseCase;
import aptech.project.educhain.domain.useCases.courses.Question.UpdateQuestionUseCase.UpdateQuestionParam;
import aptech.project.educhain.domain.useCases.courses.Question.UpdateQuestionUseCase.UpdateQuestionUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService implements IQuestionService {
    private final GetQuestionsByHomeworkUseCase getQuestionsByHomeworkUseCase;
    private final CreateQuestionUseCase createQuestionUseCase;
    private final UpdateQuestionUseCase updateQuestionUseCase;
    private final GetQuestionUseCase getQuestionUseCase;
    private final DeleteQuestionUseCase deleteQuestionUseCase;

    public QuestionService(GetQuestionsByHomeworkUseCase getQuestionsByHomeworkUseCase, CreateQuestionUseCase createQuestionUseCase, UpdateQuestionUseCase updateQuestionUseCase, GetQuestionUseCase getQuestionUseCase, DeleteQuestionUseCase deleteQuestionUseCase) {
        this.getQuestionsByHomeworkUseCase = getQuestionsByHomeworkUseCase;
        this.createQuestionUseCase = createQuestionUseCase;
        this.updateQuestionUseCase = updateQuestionUseCase;
        this.getQuestionUseCase = getQuestionUseCase;
        this.deleteQuestionUseCase = deleteQuestionUseCase;
    }

    @Override
    public AppResult<List<QuestionDTO>> getQuestionByHomework(int id) {
        return getQuestionsByHomeworkUseCase.execute(id);
    }

    @Override
    public AppResult<QuestionDTO> getQuestion(int id) {
        return getQuestionUseCase.execute(id);
    }

    @Override
    public AppResult<QuestionDTO> createQuestion(CreateQuestionParam param) {
        return createQuestionUseCase.execute(param);
    }

    @Override
    public AppResult<QuestionDTO> updateQuestion(UpdateQuestionParam param) {
        return updateQuestionUseCase.execute(param);
    }

    @Override
    public AppResult<Void> deleteQuestion(int id) {
        return deleteQuestionUseCase.execute(id);
    }


}
