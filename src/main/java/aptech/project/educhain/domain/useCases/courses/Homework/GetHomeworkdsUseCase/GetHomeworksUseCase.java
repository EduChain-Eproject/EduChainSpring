package aptech.project.educhain.domain.useCases.courses.Homework.GetHomeworkdsUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetHomeworksUseCase implements Usecase<List<HomeworkDTO>, NoParam> {
    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<List<HomeworkDTO>> execute(NoParam params) {
        try{
            List<Homework> homeworkList = homeworkRepository.findAll();
            List<HomeworkDTO> homeworkDTOList = homeworkList.stream().map(homework -> modelMapper.map(homework, HomeworkDTO.class)).toList();
            return AppResult.successResult(homeworkDTOList);
        }catch (Exception e) {
            return AppResult.failureResult(new Failure("error getting homework list: " + e.getMessage()));
        }
    }
}
