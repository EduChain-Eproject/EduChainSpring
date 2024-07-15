package aptech.project.educhain.endpoint.controllers;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.services.home.HomeService;
import aptech.project.educhain.domain.useCases.home.get_most_category.GetMostCategoryParams;
import aptech.project.educhain.endpoint.responses.home.MostPopularCourseResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private ModelMapper modelMapper;

    //get most popular course base on student
    @GetMapping("/most-popular")
    public ResponseEntity<?> getMostPopularCourse() {
        AppResult<List<CourseDTO>> result = homeService.getMostPopularCourse();
        if (result.isSuccess()) {
            List<CourseDTO> courseDTOs = result.getSuccess();
            List<MostPopularCourseResponse> responses = courseDTOs.stream()
                    .map(courseDTO -> modelMapper.map(courseDTO, MostPopularCourseResponse.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(responses);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    //get 4 categories base on course
    @GetMapping("/most-category/{limit}")
    public ResponseEntity<?> getCategoriesWithMostCourses(@PathVariable("limit") int limit) {
        GetMostCategoryParams getMostCategoryParams = new GetMostCategoryParams();
        getMostCategoryParams.setLimit(limit);
        var result = homeService.getMostPopularCategories(getMostCategoryParams);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getSuccess());
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }
}


/*
1. get 1 course có ng student tham gia nhất
2. get 4 categories có nh course nhất
3. get giáo viên có nh học sinh tham gia khoá hoc nhat
4. statistics: get tổng số enrollments
5. signature course:
6. get 3 blogs: 2 cái 5,6 ch nghĩ ra logic
*/