package aptech.project.educhain.domain.useCases.courses.course.GetCoursesByTeacherUsecase;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;

@Component
public class GetCoursesByTeacherUsecase implements Usecase<Page<CourseDTO>, GetCoursesByTeacherParams> {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppResult<Page<CourseDTO>> execute(GetCoursesByTeacherParams params) {
        try {
            Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), Sort.by(params.getSortBy()));
            Page<Course> coursesPage = courseRepository.findByTeacherIdAndTitleContaining(params.getTeacherId(),
                    params.getSearch(), pageable);

            Page<CourseDTO> coursesDtoPage = coursesPage.map(course -> modelMapper.map(course, CourseDTO.class));
            return AppResult.successResult(coursesDtoPage);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error fetching courses: " + e.getMessage()));
        }
    }

}