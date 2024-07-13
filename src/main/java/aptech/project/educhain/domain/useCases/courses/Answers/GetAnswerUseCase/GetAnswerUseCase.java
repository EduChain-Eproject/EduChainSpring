package aptech.project.educhain.domain.useCases.courses.Answers.GetAnswerUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.repositories.courses.AnswerRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetAnswerUseCase implements Usecase<AnswerDTO, Integer> {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public AppResult<AnswerDTO> execute(Integer id) {
        try {
            Optional<Answers> answersOptional = answerRepository.findById(id);
            if (!answersOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Answer not found"));
            }
            AnswerDTO answerDTO = modelMapper.map(answersOptional.get(), AnswerDTO.class);
            answerDTO.setQuestionId(answersOptional.get().getQuestion().getId());
            answerDTO.setAnswerId(answersOptional.get().getId());
            return AppResult.successResult(answerDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get homework details: " + e.getMessage()));
        }
    }
}
