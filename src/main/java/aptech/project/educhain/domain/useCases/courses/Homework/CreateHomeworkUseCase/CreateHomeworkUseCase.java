package aptech.project.educhain.domain.useCases.courses.Homework.CreateHomeworkUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Lesson;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.LessonRepository;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateHomeworkUseCase implements Usecase<HomeworkDTO, CreateHomeworkParam> {
    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    public void configureModelMapper(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(CreateHomeworkParam.class, Homework.class)
                .addMappings(mapper -> {
                    mapper.map(CreateHomeworkParam::getUserId, Homework::setUser); // Assuming Homework has a setUser method
                    mapper.map(CreateHomeworkParam::getLessonId, Homework::setLesson); // Assuming Homework has a setLesson method
                    // Explicitly define other mappings as necessary
                });
    }

    @Override
    public AppResult<HomeworkDTO> execute(CreateHomeworkParam params) {
        try {
            Optional<Lesson> lessonOptional = lessonRepository.findById(params.getLessonId());
            if (!lessonOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Lesson not found with ID: " + params.getLessonId()));
            }
            Homework homework = modelMapper.map(params, Homework.class);
            homework.setLesson(lessonOptional.get());

            User user = authUserRepository.findUserWithId(params.getUserId());

            if (user == null) {
                return AppResult.failureResult(new Failure("Failed to create homework: teacher not exist!"));
            }

            homework.setUser(user);
            Homework saveHomework = homeworkRepository.saveAndFlush(homework);
            HomeworkDTO homeworkDTO = modelMapper.map(saveHomework, HomeworkDTO.class);
            homeworkDTO.setLessonID(saveHomework.getLesson().getId());
            homeworkDTO.setUserID(saveHomework.getUser().getId());
            return AppResult.successResult(homeworkDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to create homework: " + e.getMessage()));
        }
    }
}
