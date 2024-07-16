package aptech.project.educhain.domain.useCases.courses.Question.GetQuestionUseCase;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;

@Component
public class GetQuestionUseCase implements Usecase<QuestionDTO, Integer> {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<QuestionDTO> execute(Integer id) {
        try {
            Optional<Question> questionOptional = questionRepository.findById(id);
            if (!questionOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Question not found"));
            }
            Question question = questionOptional.get();

            QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);
            questionDTO.setCorrectAnswerId(question.getCorrectAnswer().getId());
            questionDTO.setHomeworkId(question.getHomework().getId());
            List<Answers> answersList = question.getAnswers().stream().toList();
            questionDTO.setAnswerDtos(answersList.stream().map(answers -> {
                AnswerDTO answerDTO = modelMapper.map(answers, AnswerDTO.class);
                answerDTO.setQuestionId(answers.getQuestion().getId());
                answerDTO.setAnswerId(answers.getId());
                return answerDTO;
            }).toList());
            return AppResult.successResult(questionDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get question details: " + e.getMessage()));
        }
    }
}
