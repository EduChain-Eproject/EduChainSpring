package aptech.project.educhain.domain.services.courses;

import java.util.List;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;

public interface ICategoryService {

    public AppResult<List<CategoryDTO>> getListCategory(NoParam noParam);
}
