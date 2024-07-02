package aptech.project.educhain.domain.useCases.courses.course.GetCourseDetailUsecase;

import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;

@Component
public class GetCourseDetailUsecase implements Usecase<CourseDTO, Integer> {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<CourseDTO> execute(Integer courseId) {
        try {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();
                CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);
                courseDTO.setCategoryDtos(course.getCategories().stream()
                        .map(category -> modelMapper.map(category, CategoryDTO.class))
                        .collect(Collectors.toList()));
                courseDTO.setChapterDtos(course.getChapters().stream()
                        .map(chapter -> modelMapper.map(chapter, ChapterDTO.class))
                        .collect(Collectors.toList()));
                return AppResult.successResult(courseDTO);
            } else {
                return AppResult.failureResult(new Failure("Course not found with ID: " + courseId));
            }
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error getting course details: " + e.getMessage()));
        }
    }
}
