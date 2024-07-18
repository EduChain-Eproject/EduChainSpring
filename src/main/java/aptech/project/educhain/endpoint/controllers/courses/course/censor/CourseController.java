package aptech.project.educhain.endpoint.controllers.courses.course.censor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.serviceImpl.courses.CourseService;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.useCases.courses.course.ChangeCourseStatusUsecase.ChangeCourseStatusParams;
import aptech.project.educhain.domain.useCases.courses.course.SearchCoursesUseCase.CourseSearchParams;
import aptech.project.educhain.endpoint.requests.courses.course.censor.ApproveDeleteRequest;
import aptech.project.educhain.endpoint.requests.courses.course.censor.CourseSearchRequest;
import aptech.project.educhain.endpoint.responses.courses.course.censor.CourseByStatusResponse;
import aptech.project.educhain.endpoint.responses.courses.course.teacher.GetCourseDetailResponse;

@RestController("CensorCourseController")
@RequestMapping("/CENSOR/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/list")
    public ResponseEntity<?> getCourses(@RequestBody CourseSearchRequest request) {

        AppResult<Page<CourseDTO>> result = courseService.searchCourses(
                modelMapper.map(request, CourseSearchParams.class));

        if (result.isSuccess()) {
            Page<CourseDTO> courseDTOPage = result.getSuccess();
            List<CourseByStatusResponse> searchCourseResponses = courseDTOPage
                    .getContent()
                    .stream()
                    .map(courseDTO -> modelMapper.map(courseDTO, CourseByStatusResponse.class))
                    .collect(Collectors.toList());

            Page<CourseByStatusResponse> responsePage = new PageImpl<>(
                    searchCourseResponses,
                    PageRequest.of(request.getPage(), request.getSize(), Sort.by(request.getSortBy())),
                    courseDTOPage.getTotalElements());

            return ResponseEntity.ok().body(responsePage);
        }

        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @GetMapping("/detail/{courseId}")
    public ResponseEntity<?> getCourseDetail(@PathVariable("courseId") Integer courseId) {
        AppResult<CourseDTO> result = courseService.getCourseDetail(courseId);
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), GetCourseDetailResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @PostMapping("/approve-or-delete/{courseId}")
    public ResponseEntity<?> approveCourse(@PathVariable("courseId") Integer courseId,
            @RequestBody ApproveDeleteRequest request) {
        var result = courseService.changeCourseStatus(new ChangeCourseStatusParams(courseId, request.getStatus()));
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getSuccess());
        }

        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }
}
