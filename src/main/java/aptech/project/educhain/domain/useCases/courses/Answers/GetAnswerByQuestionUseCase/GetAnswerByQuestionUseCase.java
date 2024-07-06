package aptech.project.educhain.domain.useCases.courses.Answers.GetAnswerByQuestionUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.courses.AnswerRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.useCases.courses.Question.GetHomeworkdUseCase.GetQuestionUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAnswerByQuestionUseCase implements Usecase<List<AnswerDTO>, GetAnswerByQuestionParam> {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<List<AnswerDTO>> execute(GetAnswerByQuestionParam params) {
        Question question = questionRepository.findById(params.getId()).get();
        List<Answers> answersList = answerRepository.getAnswersByQuestion(question);
        List<AnswerDTO> answerDTOList = answersList.stream().map(answer -> modelMapper.map(answer, AnswerDTO.class)).toList();
        return AppResult.successResult(answerDTOList);
    }
}
