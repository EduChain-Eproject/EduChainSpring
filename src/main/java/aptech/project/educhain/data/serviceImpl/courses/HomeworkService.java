package aptech.project.educhain.data.serviceImpl.courses;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.services.courses.IHomeworkService;
import aptech.project.educhain.domain.useCases.courses.Homework.CreateHomeworkUseCase.CreateHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.CreateHomeworkUseCase.CreateHomeworkUseCase;
import aptech.project.educhain.domain.useCases.courses.Homework.DeleteHomeworkUseCase.DeleteHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.DeleteHomeworkUseCase.DeleteHomeworkUseCase;
import aptech.project.educhain.domain.useCases.courses.Homework.GetHomeworkdUseCase.GetHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.GetHomeworkdUseCase.GetHomeworkUseCase;
import aptech.project.educhain.domain.useCases.courses.Homework.GetHomeworkdsUseCase.GetHomeworksUseCase;
import aptech.project.educhain.domain.useCases.courses.Homework.UpdateHomeworkUseCase.UpdateHomeworkParam;
import aptech.project.educhain.domain.useCases.courses.Homework.UpdateHomeworkUseCase.UpdateHomeworkUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkService implements IHomeworkService {
    private final GetHomeworksUseCase getHomeworksUseCase;
    private final GetHomeworkUseCase getHomeworkUseCase;
    private final CreateHomeworkUseCase createHomeworkUseCase;
    private final UpdateHomeworkUseCase updateHomeworkUseCase;
    private final DeleteHomeworkUseCase deleteHomeworkUseCase;

    public HomeworkService(GetHomeworksUseCase getHomeworksUseCase, GetHomeworkUseCase getHomeworkUseCase, CreateHomeworkUseCase createHomeworkUseCase, UpdateHomeworkUseCase updateHomeworkUseCase, DeleteHomeworkUseCase deleteHomeworkUseCase) {
        this.getHomeworksUseCase = getHomeworksUseCase;
        this.getHomeworkUseCase = getHomeworkUseCase;
        this.createHomeworkUseCase = createHomeworkUseCase;
        this.updateHomeworkUseCase = updateHomeworkUseCase;
        this.deleteHomeworkUseCase = deleteHomeworkUseCase;
    }


    @Override
    public AppResult<List<HomeworkDTO>> getHomeworks(NoParam param) {
        return getHomeworksUseCase.execute(param);
    }

    @Override
    public AppResult<HomeworkDTO> getHomework(GetHomeworkParam param) {
        return getHomeworkUseCase.execute(param);
    }

    @Override
    public AppResult<HomeworkDTO> createHomework(CreateHomeworkParam param) {
        return createHomeworkUseCase.execute(param);
    }

    @Override
    public AppResult<HomeworkDTO> updateHomework(int id, UpdateHomeworkParam param) {
        param.setHomeworkId(id);
        return updateHomeworkUseCase.execute(param);
    }

    @Override
    public AppResult<Boolean> deleteHomework(DeleteHomeworkParam param) {
        return deleteHomeworkUseCase.execute(param);
    }
}
