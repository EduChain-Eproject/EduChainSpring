package aptech.project.educhain.domain.useCases.personalization.user_course.get_all_user_course;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.courses.UserCourse;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.data.repositories.courses.UserCourseRepository;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class GetAllUserCourseUseCase implements Usecase<Page<UserCourseDTO>, UserCourseParams> {

    @Autowired
    private UserCourseRepository userCourseRepository;
    @Autowired
    private CourseRepository courseRepository;


//    @Override
//    public AppResult<Page<UserCourseDTO>> execute(UserCourseParams params) {
//        try {
//            Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
//            Page<UserCourse> userCourses = userCourseRepository.findAllByStudentIdAndTitleSearch(params.getStudent_id(),params.getTitleSearch(),pageable);
//
//            List<UserCourseDTO> userCourseDTOs = userCourses.stream()
//                    .map(userCourse -> {
//                        User user = userCourse.getUser();
//                        Course course = userCourse.getCourse();
//                        if (course != null) {
//                            return new UserCourseDTO(
//                                    user.getFirstName(),
//                                    user.getEmail(),
//                                    course.getTitle(),
//                                    userCourse.getEnrollmentDate(),
//                                    course.getPrice(),
//                                    userCourse.getCompletionStatus(),
//                                    course.getCategories()
//                            );
//                        } else {
//                            return null; // Or handle the case where course is null
//                        }
//                    })
//                    .collect(Collectors.toList());
//
//            // Implement pagination manually
//            int start = (int) pageable.getOffset();
//            int end = Math.min((start + pageable.getPageSize()), userCourseDTOs.size());
////            Page<UserCourseDTO> userCourseDTOPage = new PageImpl<>(userCourseDTOs.subList(start, end), pageable, userCourseDTOs.size());
//            Page<UserCourseDTO> userCourseDTOPage = new PageImpl<>(userCourseDTOs, pageable, userCourses.getTotalElements());
//            return AppResult.successResult(userCourseDTOPage);
//        } catch (Exception e) {
//            return AppResult.failureResult(new Failure("Fail to get user course"));
//        }
//    }
    @Override
    public AppResult<Page<UserCourseDTO>> execute(UserCourseParams params) {
    try {
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
        Page<UserCourse> userCoursesPage = userCourseRepository.findAllByStudentIdAndTitleSearch(params.getStudent_id(), params.getTitleSearch(), pageable);

        List<UserCourseDTO> userCourseDTOs = userCoursesPage.stream()
                .map(userCourse -> {
                    User user = userCourse.getUser();
                    Course course = userCourse.getCourse();
                    if (course != null) {
                        return new UserCourseDTO(
                                user.getFirstName(),
                                user.getEmail(),
                                course.getTitle(),
                                userCourse.getEnrollmentDate(),
                                course.getPrice(),
                                userCourse.getCompletionStatus(),
                                course.getCategories()
                        );
                    } else {
                        return null; // Or handle the case where course is null
                    }
                })
                .collect(Collectors.toList());

        Page<UserCourseDTO> userCourseDTOPage = new PageImpl<>(userCourseDTOs, pageable, userCoursesPage.getTotalElements());
        return AppResult.successResult(userCourseDTOPage);
    } catch (Exception e) {
        return AppResult.failureResult(new Failure("Fail to get user course"));
    }
}

}