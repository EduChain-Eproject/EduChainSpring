package aptech.project.educhain.domain.useCases.courses.course.UpdateCourseUsecase;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.repositories.courses.CategoryRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;

@Component
public class UpdateCourseUsecase implements Usecase<CourseDTO, UpdateCourseParams> {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppResult<CourseDTO> execute(UpdateCourseParams params) {
        try {
            Optional<Course> optionalCourse = courseRepository.findById(params.getCourseId());
            if (!optionalCourse.isPresent()) {
                return AppResult.failureResult(new Failure("Course not found"));
            }

            Course course = optionalCourse.get();
            modelMapper.map(params, course);
            List<Category> categories = categoryRepository.findAllById(params.getCategoryIds());
            course.setCategories(categories);

            Course updatedCourse = courseRepository.saveAndFlush(course);
            CourseDTO courseDTO = modelMapper.map(updatedCourse, CourseDTO.class);
            return AppResult.successResult(courseDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error updating course: " + e.getMessage()));
        }
    }
}
