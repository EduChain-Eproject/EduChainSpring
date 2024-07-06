package aptech.project.educhain.domain.useCases.courses.Homework.CreateHomeworkUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CreateHomeworkUseCase implements Usecase<HomeworkDTO, CreateHomeworkParam> {
    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthUserRepository authUserRepository;

    @Override
    public AppResult<HomeworkDTO> execute(CreateHomeworkParam params) {
        try {
            Homework homework = modelMapper.map(params, Homework.class);
            User user = authUserRepository.findUserWithId(params.getUserId());

            if (user == null) {
                return AppResult.failureResult(new Failure("Failed to create course: teacher not exist!"));
            }
            homework.setUser(user);
            Homework saveHomework = homeworkRepository.saveAndFlush(homework);
            HomeworkDTO homeworkDTO = modelMapper.map(saveHomework, HomeworkDTO.class);
            return AppResult.successResult(homeworkDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to create homework: " + e.getMessage()));
        }
    }
}
