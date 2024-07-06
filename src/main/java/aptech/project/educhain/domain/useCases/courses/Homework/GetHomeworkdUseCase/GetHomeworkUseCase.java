package aptech.project.educhain.domain.useCases.courses.Homework.GetHomeworkdUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetHomeworkUseCase implements Usecase<HomeworkDTO, GetHomeworkParam> {
    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<HomeworkDTO> execute(GetHomeworkParam params) {
        return AppResult.successResult(modelMapper.map(homeworkRepository.findById(params.getId()).get(), HomeworkDTO.class));
    }
}
