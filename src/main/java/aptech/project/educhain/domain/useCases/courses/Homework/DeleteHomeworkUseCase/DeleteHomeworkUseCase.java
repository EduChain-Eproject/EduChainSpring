package aptech.project.educhain.domain.useCases.courses.Homework.DeleteHomeworkUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteHomeworkUseCase  implements Usecase<Void, DeleteHomeworkParam> {
    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<Void> execute(DeleteHomeworkParam param) {
        try {
            if (homeworkRepository.existsById(param.getId())) {
                homeworkRepository.deleteById(param.getId());
                return AppResult.successResult(null);
            } else {
                return AppResult.failureResult(new Failure("Homework not found with ID: " + param.getId()));
            }
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error deleting homework: " + e.getMessage()));
        }
    }
}
