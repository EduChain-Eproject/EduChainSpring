package aptech.project.educhain.domain.useCases.courses.course.GetRelatedCoursesUsecase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.courses.CourseStatus;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;

@Component
public class GetRelatedCoursesUsecase implements Usecase<List<CourseDTO>, Integer> {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppResult<List<CourseDTO>> execute(Integer courseId) {
        try {
            Optional<Course> courseOpt = courseRepository.findById(courseId);
            if (courseOpt.isPresent()) {
                Course course = courseOpt.get();
                List<Category> categories = course.getCategories();

                List<Course> relatedCourses = courseRepository.findDistinctByCategoriesInAndIdNot(categories, courseId, CourseStatus.APPROVED);
                List<CourseDTO> relatedCourseDtos = relatedCourses.stream()
                        .map(relatedCourse -> modelMapper.map(relatedCourse, CourseDTO.class))
                        .collect(Collectors.toList());

                return AppResult.successResult(relatedCourseDtos);
            } else {
                return AppResult.failureResult(new Failure("Course not found"));
            }
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get related courses: " + e.getMessage()));
        }
    }
}
