package aptech.project.educhain.domain.useCases.courses.Homework.UpdateHomeworkUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.useCases.courses.Homework.CreateHomeworkUseCase.CreateHomeworkParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateHomeworkUseCase implements Usecase<HomeworkDTO, UpdateHomeworkParam> {
    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthUserRepository authUserRepository;

    @Override
    public AppResult<HomeworkDTO> execute(UpdateHomeworkParam params) {
        try {
            Homework hw = homeworkRepository.findById(params.getHomeworkId()).get();
            if(hw != null){
                Homework homework = modelMapper.map(params, Homework.class);
                homework.setTitle(params.getTitle());
                homework.setDescription(params.getDescription());
                Homework saveHomework = homeworkRepository.saveAndFlush(homework);
                HomeworkDTO homeworkDTO = modelMapper.map(saveHomework, HomeworkDTO.class);
                return AppResult.successResult(homeworkDTO);
            }
            return AppResult.failureResult(new Failure("Can not find homework: "));
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to create homework: " + e.getMessage()));
        }
    }
}
