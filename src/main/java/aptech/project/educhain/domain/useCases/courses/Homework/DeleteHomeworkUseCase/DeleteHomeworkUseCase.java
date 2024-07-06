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
public class DeleteHomeworkUseCase  implements Usecase<Boolean, DeleteHomeworkParam> {
    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<Boolean> execute(DeleteHomeworkParam params) {
        try{
            Homework homework = homeworkRepository.findById(params.getId()).get();
            homeworkRepository.delete(homework);
            return AppResult.successResult(true);
        }catch(Exception ex){
            return AppResult.failureResult(new Failure("Failed to delete homework: " + ex.getMessage()));
        }
    }
}
