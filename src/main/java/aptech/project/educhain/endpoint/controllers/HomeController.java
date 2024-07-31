package aptech.project.educhain.endpoint.controllers;

import java.util.List;
import java.util.stream.Collectors;

import aptech.project.educhain.common.result.ApiError;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.home.PopularTeacherDTO;
import aptech.project.educhain.domain.dtos.home.Statistics;
import aptech.project.educhain.domain.services.home.HomeService;
import aptech.project.educhain.domain.useCases.home.get_most_category.GetMostCategoryParams;
import aptech.project.educhain.endpoint.responses.home.MostPopularCourseResponse;
import aptech.project.educhain.endpoint.responses.home.MostPopularTeacherResponse;

@RestController
@RequestMapping("/HOME/api")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private ModelMapper modelMapper;

    // get most popular course base on student
    //todo
    @GetMapping("/signature-courses")
    public ResponseEntity<?> getMostPopularCourse() {
        AppResult<List<CourseDTO>> result = homeService.getMostPopularCourse();
        if (result.isSuccess()) {
            List<CourseDTO> courseDTOs = result.getSuccess();
            List<MostPopularCourseResponse> responses = courseDTOs.stream()
                    .map(courseDTO -> modelMapper.map(courseDTO, MostPopularCourseResponse.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(responses);
        }
        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()), HttpStatus.BAD_REQUEST);
    }

    // get 4 categories base on course
    //todo
    @GetMapping("/best-categories")
    public ResponseEntity<?> getCategoriesWithMostCourses() {
        GetMostCategoryParams getMostCategoryParams = new GetMostCategoryParams();
        getMostCategoryParams.setLimit(4);
        var result = homeService.getMostPopularCategories(getMostCategoryParams);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getSuccess());
        }
        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()), HttpStatus.BAD_REQUEST);
    }

    //todo
    @GetMapping("/most-popular-teacher")
    public ResponseEntity<?> getMostPopularTeacher() {
        AppResult<PopularTeacherDTO> result = homeService.getMostPopularTeacher();
        if (result.isSuccess()) {
            MostPopularTeacherResponse teacher = modelMapper.map(result.getSuccess(), MostPopularTeacherResponse.class);
            return ResponseEntity.ok(teacher);
        }
        return new ResponseEntity<>(new ApiError(result.getFailure().getMessage()), HttpStatus.BAD_REQUEST);
    }

    //todo
    @GetMapping("/statistics")
    public ResponseEntity<?> statistics() {
        AppResult<Statistics> statistics = homeService.getStatistics();
        
        if (statistics.isSuccess()) {
            return ResponseEntity.ok(statistics.getSuccess());
        }
        return new ResponseEntity<>(new ApiError(statistics.getFailure().getMessage()), HttpStatus.BAD_REQUEST);
    }
}

/*
 * 1. get 1 course có ng student tham gia nhất
 * 2. get 4 categories có nh course nhất
 * 3. get giáo viên có nh học sinh tham gia khoá hoc nhat
 * 4. statistics: get tổng số enrollments
 * 5. signature course:
 * 6. get 3 blogs: 2 cái 5,6 ch nghĩ ra logic
 */