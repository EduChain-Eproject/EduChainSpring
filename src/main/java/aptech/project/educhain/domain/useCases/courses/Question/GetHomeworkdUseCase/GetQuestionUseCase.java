package aptech.project.educhain.domain.useCases.courses.Question.GetHomeworkdUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetQuestionUseCase implements Usecase<QuestionDTO, GetQuestionParam> {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<QuestionDTO> execute(GetQuestionParam params) {
        return AppResult.successResult(modelMapper.map(questionRepository.findById(params.getId()).get(), QuestionDTO.class));
    }
}
