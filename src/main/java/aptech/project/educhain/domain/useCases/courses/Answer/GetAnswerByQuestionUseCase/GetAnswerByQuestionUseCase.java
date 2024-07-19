package aptech.project.educhain.domain.useCases.courses.Answer.GetAnswerByQuestionUseCase;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Answer;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.courses.AnswerRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;

@Component
public class GetAnswerByQuestionUseCase implements Usecase<List<AnswerDTO>, Integer> {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<List<AnswerDTO>> execute(Integer id) {
        try {
            Question qs = questionRepository.findById(id).get();
            if (qs == null) {
                return AppResult.failureResult(new Failure("can not find question with id: " + id));
            }
            List<Answer> answers = answerRepository.getAnswersByQuestion(qs);
            List<AnswerDTO> answerDTOList = answers.stream().map(ans -> {
                AnswerDTO dto = modelMapper.map(ans, AnswerDTO.class);
                dto.setQuestionId(ans.getQuestion().getId());
                dto.setId(ans.getId());
                return dto;
            }).toList();
            ;
            return AppResult.successResult(answerDTOList);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("error getting answer list: " + e.getMessage()));
        }
    }
}
