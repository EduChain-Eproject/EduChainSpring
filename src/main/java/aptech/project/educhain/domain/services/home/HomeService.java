package aptech.project.educhain.domain.services.home;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.useCases.home.get_most_category.GetMostCategoryParams;

import java.util.List;

public interface HomeService {
    AppResult<List<CourseDTO>> getMostPopularCourse();
    AppResult<List<CategoryDTO>> getMostPopularCategories(GetMostCategoryParams params);
}
