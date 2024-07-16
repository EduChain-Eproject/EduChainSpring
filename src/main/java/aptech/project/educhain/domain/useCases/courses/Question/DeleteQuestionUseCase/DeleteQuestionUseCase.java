package aptech.project.educhain.domain.useCases.courses.Question.DeleteQuestionUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteQuestionUseCase  implements Usecase<Void, Integer> {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public AppResult<Void> execute(Integer id) {
        try {
            if (questionRepository.existsById(id)) {
                questionRepository.deleteById(id);
                return AppResult.successResult(null);
            } else {
                return AppResult.failureResult(new Failure("Question not found with ID: " + id));
            }
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error deleting question: " + e.getMessage()));
        }
    }
}
