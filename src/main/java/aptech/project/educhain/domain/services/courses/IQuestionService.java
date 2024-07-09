package aptech.project.educhain.domain.services.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import aptech.project.educhain.domain.useCases.courses.Question.CreateQuestionUseCase.CreateQuestionParam;
import aptech.project.educhain.domain.useCases.courses.Question.UpdateQuestionUseCase.UpdateQuestionParam;
import org.apache.coyote.http11.filters.VoidInputFilter;

import java.util.List;

public interface IQuestionService {
    public AppResult<List<QuestionDTO>> getQuestionByHomework(int id);

    public AppResult<QuestionDTO> getQuestion(int id);

    public AppResult<QuestionDTO> createQuestion(CreateQuestionParam param);

    public AppResult<QuestionDTO> updateQuestion(UpdateQuestionParam param);

    public AppResult<Void> deleteQuestion(int id);
}
