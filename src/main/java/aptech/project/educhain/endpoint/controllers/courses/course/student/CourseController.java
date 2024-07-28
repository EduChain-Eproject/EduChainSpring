package aptech.project.educhain.endpoint.controllers.courses.course.student;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.CourseStatus;
import aptech.project.educhain.data.serviceImpl.courses.CourseService;
import aptech.project.educhain.data.serviceImpl.personalization.UserCourseServiceImpl;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.useCases.courses.course.SearchCoursesUseCase.CourseSearchParams;
import aptech.project.educhain.domain.useCases.personalization.user_course.add_user_course.AddUserCourseParams;
import aptech.project.educhain.domain.useCases.personalization.user_course.get_user_course.GetUserCourseParams;
import aptech.project.educhain.endpoint.requests.courses.course.student.CourseSearchRequest;
import aptech.project.educhain.endpoint.responses.courses.course.student.GetCourseDetailResponse;
import aptech.project.educhain.endpoint.responses.courses.course.student.SearchCourseResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController("StudentCourseController")
@RequestMapping("/STUDENT/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserCourseServiceImpl userCourseService;

    @Autowired
    IJwtService iJwtService;

    @PostMapping("/list")
    public ResponseEntity<?> getCourses(@RequestBody CourseSearchRequest request) {

        var params = modelMapper.map(request, CourseSearchParams.class);
        params.setStatus(CourseStatus.APPROVED);

        AppResult<Page<CourseDTO>> result = courseService.searchCourses(params);

        if (result.isSuccess()) {
            Page<CourseDTO> courseDTOPage = result.getSuccess();
            List<SearchCourseResponse> searchCourseResponses = courseDTOPage
                    .getContent()
                    .stream()
                    .map(courseDTO -> modelMapper.map(courseDTO, SearchCourseResponse.class))
                    .collect(Collectors.toList());

            Page<SearchCourseResponse> responsePage = new PageImpl<>(
                    searchCourseResponses,
                    PageRequest.of(request.getPage(), request.getSize(), Sort.by(request.getSortBy())),
                    courseDTOPage.getTotalElements());

            return ResponseEntity.ok().body(responsePage);
        }

        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @GetMapping("/detail/{courseId}")
    public ResponseEntity<?> getCourseDetail(@PathVariable Integer courseId, HttpServletRequest request) {
        var user = iJwtService.getUserByHeaderToken(request.getHeader("Authorization"));

        AppResult<CourseDTO> result = courseService.getCourseDetail(courseId);

        if (result.isSuccess()) {
            var successValue = result.getSuccess();

            if (user != null) {
                AppResult<UserCourseDTO> result2 = userCourseService
                        .getUserCourse(new GetUserCourseParams(user.getId(), courseId));

                successValue.setNumberOfEnrolledStudents(successValue.getParticipatedUserDtos().size());

                if (result2.isSuccess()) {
                    successValue.setCurrentUserCourse(result2.getSuccess());
                }
            }
            AppResult<List<CourseDTO>> relatedCoursesResult = courseService.getRelatedCourses(courseId);
            if (relatedCoursesResult.isSuccess()) {
                successValue.setRelatedCourseDtos(relatedCoursesResult.getSuccess());
            }

            var res = modelMapper.map(successValue, GetCourseDetailResponse.class);

            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @PostMapping("/enroll-in-a-course/{courseId}")
    public ResponseEntity<?> enroll(@PathVariable Integer courseId, HttpServletRequest request) {
        User user = iJwtService.getUserByHeaderToken(request.getHeader("Authorization"));

        AddUserCourseParams addUserCourseParams = new AddUserCourseParams();
        addUserCourseParams.setCourse_id(courseId);
        addUserCourseParams.setStudent_id(user.getId());

        var userCourse = userCourseService.addUserCourseWithParams(addUserCourseParams);

        if (userCourse.isSuccess()) {
            return new ResponseEntity<>(userCourse.getSuccess(), HttpStatus.OK);
        }
        return new ResponseEntity<>(userCourse.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }
}
