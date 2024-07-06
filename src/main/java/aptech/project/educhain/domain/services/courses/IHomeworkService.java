package aptech.project.educhain.domain.services.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.useCases.courses.Homework.CreateHomeworkUseCase.CreateHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.DeleteHomeworkUseCase.DeleteHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.GetHomeworkdUseCase.GetHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.UpdateHomeworkUseCase.UpdateHomeworkParam;

import java.util.List;

public interface IHomeworkService {
    public AppResult<List<HomeworkDTO>> getHomeworks(NoParam param);

    public AppResult<HomeworkDTO> getHomework(GetHomeworkParam param);

    public AppResult<HomeworkDTO> createHomework(CreateHomeworkParam param);

    public AppResult<HomeworkDTO> updateHomework(int id, UpdateHomeworkParam param);

    public AppResult<Boolean> deleteHomework(DeleteHomeworkParam param);
}
