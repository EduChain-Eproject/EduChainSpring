package aptech.project.educhain.domain.useCases.courses.Answers.GetAnswerByQuestionUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.courses.AnswerRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
        try{
            Question qs = questionRepository.findById(id).get();
            if(qs == null){
                return AppResult.failureResult(new Failure("can not find question with id: " + id));
            }
            List<Answers> answers = answerRepository.getAnswersByQuestion(qs);
            List<AnswerDTO> answerDTOList = answers.stream().map(ans -> {
                AnswerDTO dto = modelMapper.map(ans, AnswerDTO.class);
                dto.setQuestionId(ans.getQuestion().getId());
                dto.setAnswerId(ans.getId());
                return dto;
            }).toList();;
            return AppResult.successResult(answerDTOList);
        }catch (Exception e) {
            return AppResult.failureResult(new Failure("error getting answer list: " + e.getMessage()));
        }
    }
}
