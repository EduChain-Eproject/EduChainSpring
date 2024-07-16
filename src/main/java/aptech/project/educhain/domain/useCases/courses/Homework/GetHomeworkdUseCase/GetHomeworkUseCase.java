package aptech.project.educhain.domain.useCases.courses.Homework.GetHomeworkdUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Chapter;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GetHomeworkUseCase implements Usecase<HomeworkDTO, GetHomeworkParam> {
    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<HomeworkDTO> execute(GetHomeworkParam params) {
        try {
            Optional<Homework> homeworkOpt = homeworkRepository.findById(params.getId());
            if (!homeworkOpt.isPresent()) {
                return AppResult.failureResult(new Failure("Homework not found"));
            }
            HomeworkDTO homeworkDTO = modelMapper.map(homeworkOpt.get(), HomeworkDTO.class);
            homeworkDTO.setLessonID(homeworkOpt.get().getLesson().getId());
            return AppResult.successResult(homeworkDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get homework details: " + e.getMessage()));
        }
    }

}
