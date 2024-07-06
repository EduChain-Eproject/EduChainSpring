package aptech.project.educhain.domain.useCases.courses.Answers.GetAnswerUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.repositories.courses.AnswerRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public class GetAnswerUseCase implements Usecase<AnswerDTO, GetAnswerParam> {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public AppResult<AnswerDTO> execute(GetAnswerParam params) {
        Answers answers = answerRepository.findById(params.getAnswerId()).get();
        return  AppResult.successResult(modelMapper.map(answers, AnswerDTO.class));
    }
}
