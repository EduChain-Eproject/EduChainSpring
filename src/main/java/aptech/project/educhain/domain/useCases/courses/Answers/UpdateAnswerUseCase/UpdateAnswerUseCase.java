package aptech.project.educhain.domain.useCases.courses.Answers.UpdateAnswerUseCase;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.repositories.courses.AnswerRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;

@Component
public class UpdateAnswerUseCase implements Usecase<AnswerDTO, UpdateAnswerParam> {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<AnswerDTO> execute(UpdateAnswerParam params) {
        try {
            Optional<Answers> answersOptional = answerRepository.findById(params.getId());
            if (!answersOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Answer not found with ID: " + params.getId()));
            }

            Answers ans = answersOptional.get();
            ans.setAnswerText(params.getAnswerText());

            Answers saveAnswer = answerRepository.saveAndFlush(ans);

            AnswerDTO answerDTO = modelMapper.map(saveAnswer, AnswerDTO.class);
            answerDTO.setQuestionId(ans.getQuestion().getId());
            answerDTO.setId(ans.getId());
            return AppResult.successResult(answerDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to update homework: " + e.getMessage()));
        }
    }
}
