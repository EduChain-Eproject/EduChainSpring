package aptech.project.educhain.domain.useCases.courses.Question.GetHomeworkdsUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetQuestionsUseCase implements Usecase<List<QuestionDTO>, NoParam> {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<List<QuestionDTO>> execute(NoParam params) {
        List<Question> questionList = questionRepository.findAll();
        List<QuestionDTO> questionDTOList = questionList.stream().map(question -> modelMapper.map(question, QuestionDTO.class)).toList();
        return AppResult.successResult(questionDTOList);
    }
}
