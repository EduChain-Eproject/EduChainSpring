package aptech.project.educhain.domain.useCases.home.get_most_category;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.repositories.courses.CourseCategoryRepository;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetMostCategoryUseCase implements Usecase<List<CategoryDTO>,GetMostCategoryParams> {
    @Autowired
    CourseCategoryRepository courseCategoryRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<List<CategoryDTO>> execute(GetMostCategoryParams params) {
        try {
            List<Category> categories = courseCategoryRepository.findTopCategoriesWithMostCourses(PageRequest.of(0, params.getLimit()));
            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(category -> modelMapper.map(category, CategoryDTO.class))
                    .collect(Collectors.toList());
            return AppResult.successResult(categoryDTOs);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error retrieving categories with most courses: " + e.getMessage()));
        }
    }
}
