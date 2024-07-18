package aptech.project.educhain.domain.useCases.courses.Answer.GetAnswerUseCase;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Answer;
import aptech.project.educhain.data.repositories.courses.AnswerRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;

@Component
public class GetAnswerUseCase implements Usecase<AnswerDTO, Integer> {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<AnswerDTO> execute(Integer id) {
        try {
            Optional<Answer> answerOptional = answerRepository.findById(id);
            if (!answerOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Answer not found"));
            }
            AnswerDTO answerDTO = modelMapper.map(answerOptional.get(), AnswerDTO.class);
            answerDTO.setQuestionId(answerOptional.get().getQuestion().getId());
            answerDTO.setId(answerOptional.get().getId());
            return AppResult.successResult(answerDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get homework details: " + e.getMessage()));
        }
    }
}
