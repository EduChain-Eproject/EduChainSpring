package aptech.project.educhain.domain.useCases.courses.Question.GetQuestionsByHomeworkUseCase;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Answer;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;

@Component
public class GetQuestionsByHomeworkUseCase implements Usecase<List<QuestionDTO>, Integer> {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<List<QuestionDTO>> execute(Integer id) {
        try {
            Optional<Homework> hwOptional = homeworkRepository.findById(id);
            if (!hwOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Cannot find homework with id: " + id));
            }
            Homework hw = hwOptional.get();
            List<Question> questionList = questionRepository.findQuestionByHomework(hw);

            List<QuestionDTO> questionDTOList = questionList.stream().map(question -> {
                QuestionDTO dto = modelMapper.map(question, QuestionDTO.class);
                if (question.getCorrectAnswer() != null) {
                    dto.setCorrectAnswerId(question.getCorrectAnswer().getId());
                }

                List<Answer> answersList = question.getAnswers().stream().toList();
                List<AnswerDTO> answerDTOList = answersList.stream().map(answers -> {
                    AnswerDTO answerDTO = modelMapper.map(answers, AnswerDTO.class);
                    answerDTO.setQuestionId(answers.getQuestion().getId());
                    answerDTO.setId(answers.getId());
                    return answerDTO;
                }).toList();
                dto.setAnswerDtos(answerDTOList);
                return dto;
            }).toList();

            return AppResult.successResult(questionDTOList);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error getting homework list: " + e.getMessage()));
        }
    }
}
