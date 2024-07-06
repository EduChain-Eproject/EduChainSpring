package aptech.project.educhain.domain.useCases.courses.Question.DeleteHomeworkUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteQuestionUseCase  implements Usecase<Boolean, DeleteQuestionParam> {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<Boolean> execute(DeleteQuestionParam params) {
        try{
            Question question = questionRepository.findById(params.getId()).get();
            questionRepository.delete(question);
            return AppResult.successResult(true);
        }catch(Exception ex){
            return AppResult.failureResult(new Failure("Failed to delete question: " + ex.getMessage()));
        }
    }
}
