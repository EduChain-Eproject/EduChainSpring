package aptech.project.educhain.domain.useCases.courses.course.ChangeCourseStatusUsecase;

import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.CourseStatus;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;

@Component
public class ChangeCourseStatusUsecase implements Usecase<CourseDTO, ChangeCourseStatusParams> {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<CourseDTO> execute(ChangeCourseStatusParams params) {
        try {
            Optional<Course> optionalCourse = courseRepository.findById(params.getCourseId());
            if (!optionalCourse.isPresent()) {
                return AppResult.failureResult(new Failure("Course not found"));
            }

            Course course = optionalCourse.get();

            if (params.getStatus() == null) {
                course.setStatus(
                        course.getStatus() == CourseStatus.DEACTIVATED
                                ? CourseStatus.REACTIVATED
                                : CourseStatus.DEACTIVATED);
            } else {
                course.setStatus(params.getStatus());
            }

            courseRepository.save(course);

            CourseDTO deletedCourseDto = modelMapper.map(course, CourseDTO.class);
            deletedCourseDto.setCategoryDtos(course.getCategories().stream()
                    .map(category -> modelMapper.map(category, CategoryDTO.class))
                    .collect(Collectors.toList()));

            return AppResult.successResult(deletedCourseDto);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error deleting course: " + e.getMessage()));
        }
    }
}