package aptech.project.educhain.domain.useCases.courses.category.GetListCategoryUsecase;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.repositories.courses.CategoryRepository;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;

@Component
public class GetListCategoryUsecase implements Usecase<List<CategoryDTO>, NoParam> {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<List<CategoryDTO>> execute(NoParam params) {
        try {

            var listCategories = categoryRepository.findAll();
            var listCategoryDtos = listCategories
                    .stream()
                    .map(c -> modelMapper.map(c, CategoryDTO.class))
                    .collect(Collectors.toList());

            return AppResult.successResult(listCategoryDtos);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("error getting categories: " + e.getMessage()));
        }
    }

}
