package aptech.project.educhain.data.serviceImpl.courses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.services.courses.ICategoryService;
import aptech.project.educhain.domain.useCases.courses.category.GetListCategoryUsecase.GetListCategoryUsecase;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    GetListCategoryUsecase getListCategoryUsecase;

    @Override
    public AppResult<List<CategoryDTO>> getListCategory(NoParam noParam) {
        return getListCategoryUsecase.execute(noParam);
    }

}
