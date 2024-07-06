package aptech.project.educhain.domain.useCases.courses.Answers.UpdateAnswerUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.AnswerRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import aptech.project.educhain.domain.useCases.courses.Question.UpdateHomeworkUseCase.UpdateQuestionParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateAnswerUseCase implements Usecase<AnswerDTO, UpdateAnswerParam> {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<AnswerDTO> execute(UpdateAnswerParam params) {
        try {
            Answers answers = modelMapper.map(params, Answers.class);
            answers.setAnswerText(params.getAnswerText());

            Answers saveAnswer = answerRepository.saveAndFlush(answers);
            AnswerDTO answerDTO = modelMapper.map(saveAnswer, AnswerDTO.class);
            return AppResult.successResult(answerDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to update: " + e.getMessage()));
        }
    }
}
