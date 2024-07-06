package aptech.project.educhain.domain.useCases.courses.Question.UpdateHomeworkUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import aptech.project.educhain.domain.useCases.courses.Question.CreateHomeworkUseCase.CreateQuestionParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateQuestionUseCase implements Usecase<QuestionDTO, UpdateQuestionParam> {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthUserRepository authUserRepository;

    @Override
    public AppResult<QuestionDTO> execute(UpdateQuestionParam params) {
        try {
            Question question = modelMapper.map(params, Question.class);
            question.setQuestionText(params.getQuestionText());
            question.setCorrectAnswer(params.getCorrectAnswer());

            Question saveQuestion = questionRepository.saveAndFlush(question);
            QuestionDTO questionDTO = modelMapper.map(saveQuestion, QuestionDTO.class);
            return AppResult.successResult(questionDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to create homework: " + e.getMessage()));
        }
    }
}
