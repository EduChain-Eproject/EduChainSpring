package aptech.project.educhain.domain.useCases.courses.Homework.UpdateHomeworkUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Chapter;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Lesson;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.LessonRepository;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.useCases.courses.Homework.CreateHomeworkUseCase.CreateHomeworkParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateHomeworkUseCase implements Usecase<HomeworkDTO, UpdateHomeworkParam> {
    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Override
    public AppResult<HomeworkDTO> execute(UpdateHomeworkParam params) {
        try {
            Optional<Homework> homeworkOptional = homeworkRepository.findById(params.getId());
            if (!homeworkOptional.isPresent()) {
                return AppResult.failureResult(new Failure("Homework not found with ID: " + params.getId()));
            }

            Homework hw = homeworkOptional.get();
            hw.setTitle(params.getTitle());
            hw.setDescription(params.getDescription());

            Homework saveHomework = homeworkRepository.saveAndFlush(hw);

            HomeworkDTO homeworkDTO = modelMapper.map(saveHomework, HomeworkDTO.class);
            homeworkDTO.setLessonID(hw.getLesson().getId());
            homeworkDTO.setUserID(hw.getUser().getId());
            return AppResult.successResult(homeworkDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to update homework: " + e.getMessage()));
        }
    }
}
