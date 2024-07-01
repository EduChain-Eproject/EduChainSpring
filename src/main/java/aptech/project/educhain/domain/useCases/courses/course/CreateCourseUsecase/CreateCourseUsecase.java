package aptech.project.educhain.domain.useCases.courses.course.CreateCourseUsecase;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.CategoryRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;

@Component
public class CreateCourseUsecase implements Usecase<CourseDTO, CreateCourseParams> {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppResult<CourseDTO> execute(CreateCourseParams params) {
        try {
            Course course = modelMapper.map(params, Course.class);

            List<Category> categories = categoryRepository.findAllById(params.getCategoryId());
            var teacher = authUserRepository.findById(params.getTeacherId());

            if (!teacher.isPresent()) {
                return AppResult.failureResult(new Failure("Failed to create course: Teacher not exist!"));
            }
            course.setTeacher(teacher.get());
            course.setCategories(categories);

            Course savedCourse = courseRepository.saveAndFlush(course);

            CourseDTO courseDTO = modelMapper.map(savedCourse, CourseDTO.class);
            courseDTO.setCategoryDtos(savedCourse.getCategories().stream()
                    .map(category -> modelMapper.map(category, CategoryDTO.class))
                    .collect(Collectors.toList()));
            return AppResult.successResult(courseDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to create course: " + e.getMessage()));
        }
    }
}
