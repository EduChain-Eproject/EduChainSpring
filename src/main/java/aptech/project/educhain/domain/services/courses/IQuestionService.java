package aptech.project.educhain.domain.services.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;

import java.util.List;

public interface IQuestionService {
    public AppResult<List<QuestionDTO>> getQuestionByHomework(int id);

    public AppResult<HomeworkDTO> getQuestion(int id);

    public AppResult<HomeworkDTO> createQuestion(Question newQuestion);

    public AppResult<HomeworkDTO> updateQuestion(Question question);

    public AppResult<Boolean> DeleteQuestion(int id);
}
