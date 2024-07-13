package aptech.project.educhain.endpoint.controllers.courses.category.common;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.data.serviceImpl.courses.CategoryService;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.endpoint.responses.courses.category.teacher.GetListCategoryResponse;

@RestController
@RequestMapping("/COMMON/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("list")
    public ResponseEntity<?> getMethodName() {
        AppResult<List<CategoryDTO>> result = categoryService.getListCategory(NoParam.get());
        if (result.isSuccess()) {
            var res = result
                    .getSuccess()
                    .stream()
                    .map(dto -> modelMapper.map(dto, GetListCategoryResponse.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

}
