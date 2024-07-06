package aptech.project.educhain.data.serviceImpl.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import aptech.project.educhain.domain.services.courses.IQuestionService;

import java.util.List;

public class QuestionService implements IQuestionService {
    @Override
    public AppResult<List<QuestionDTO>> getQuestionByHomework(int id) {
        return null;
    }

    @Override
    public AppResult<HomeworkDTO> getQuestion(int id) {
        return null;
    }

    @Override
    public AppResult<HomeworkDTO> createQuestion(Question newQuestion) {
        return null;
    }

    @Override
    public AppResult<HomeworkDTO> updateQuestion(Question question) {
        return null;
    }

    @Override
    public AppResult<Boolean> DeleteQuestion(int id) {
        return null;
    }
}
