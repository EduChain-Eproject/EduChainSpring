package aptech.project.educhain.data.serviceImpl.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.services.courses.course.ICourseService;
import aptech.project.educhain.domain.useCases.courses.course.CreateCourseUsecase.CreateCourseParams;
import aptech.project.educhain.domain.useCases.courses.course.CreateCourseUsecase.CreateCourseUsecase;

@Service
public class CourseService implements ICourseService {
    @Autowired
    CreateCourseUsecase createCourseUsecase;

    @Override
    public AppResult<CourseDTO> createCourse(CreateCourseParams params) {
        return createCourseUsecase.execute(params);
    }
}
