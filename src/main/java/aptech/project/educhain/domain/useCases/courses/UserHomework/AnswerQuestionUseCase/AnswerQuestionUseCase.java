package aptech.project.educhain.domain.useCases.courses.UserHomework.AnswerQuestionUseCase;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.UserAnswer;
import aptech.project.educhain.data.entities.courses.UserHomework;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.AnswerRepository;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.data.repositories.courses.UserAnswerRepository;
import aptech.project.educhain.data.repositories.courses.UserHomeworkRepository;
import aptech.project.educhain.domain.dtos.courses.UserAnswerDTO;

@Component
public class AnswerQuestionUseCase implements Usecase<UserAnswerDTO, AnswerQuestionParams> {
    @Autowired
    UserHomeworkRepository userHomeworkRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    UserAnswerRepository userAnswerRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<UserAnswerDTO> execute(AnswerQuestionParams params) {
        try {
            Optional<UserHomework> userHomeworkOptional = userHomeworkRepository
                    .findByUserIdAndHomeworkId(params.getUserId(), params.getHomeworkId());
            if (!userHomeworkOptional.isPresent()) {
                return AppResult.failureResult(new Failure("User homework not found"));
            }

            UserHomework userHomework = userHomeworkOptional.get();

            Optional<UserAnswer> userAnswerOptional = userAnswerRepository.findByUserIdAndQuestionId(params.getUserId(),
                    params.getQuestionId());
            UserAnswer userAnswer;
            if (userAnswerOptional.isPresent()) {
                userAnswer = userAnswerOptional.get();
                userAnswer.setAnswer(answerRepository.findById(params.getAnswerId()).get());
            } else {
                userAnswer = new UserAnswer();
                userAnswer.setUser(authUserRepository.findById(params.getUserId()).get());
                userAnswer.setUserHomework(userHomework);
                userAnswer.setQuestion(questionRepository.findById(params.getQuestionId()).get());
                userAnswer.setAnswer(answerRepository.findById(params.getAnswerId()).get());
            }

            userAnswer = userAnswerRepository.save(userAnswer);

            var dto = modelMapper.map(userAnswer, UserAnswerDTO.class);
            dto.setAnswerId(params.getAnswerId());
            dto.setQuestionId(params.getQuestionId());
            dto.setUserHomeworkId(userHomework.getId());

            return AppResult.successResult(dto);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to answer a question: " + e.getMessage()));
        }
    }

}
