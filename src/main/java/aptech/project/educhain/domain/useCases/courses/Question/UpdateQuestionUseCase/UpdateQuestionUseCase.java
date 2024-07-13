package aptech.project.educhain.domain.useCases.courses.Question.UpdateQuestionUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.AnswerRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UpdateQuestionUseCase implements Usecase<QuestionDTO, UpdateQuestionParam> {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthUserRepository authUserRepository;

    @Override
    public AppResult<QuestionDTO> execute(UpdateQuestionParam params) {
        try {
            Optional<Question> homeworkOptional = questionRepository.findById(params.getId());
            if (!homeworkOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Question not found with ID: " + params.getId()));
            }

            Question question = homeworkOptional.get();
            question.setQuestionText(params.getQuestionText());
            question.setCorrectAnswer(answerRepository.findById(params.getCorrectAnswerId()).get());

            Question saveQuestion = questionRepository.saveAndFlush(question);

            QuestionDTO questionDTO = modelMapper.map(saveQuestion, QuestionDTO.class);
            questionDTO.setCorrectAnswerId(saveQuestion.getCorrectAnswer().getId());
            questionDTO.setHomeworkId(saveQuestion.getHomework().getId());
            List<Answers> answersList = question.getAnswers().stream().toList();
            questionDTO.setAnswers(answersList.stream().map(answers ->{
                        AnswerDTO answerDTO = modelMapper.map(answers, AnswerDTO.class);
                        answerDTO.setQuestionId(answers.getQuestion().getId());
                        answerDTO.setAnswerId(answers.getId());
                        return answerDTO;
                    }
            ).toList());

            return AppResult.successResult(questionDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to update homework: " + e.getMessage()));
        }
    }
}
