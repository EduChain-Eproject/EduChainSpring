package aptech.project.educhain.data.serviceImpl.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.services.courses.ICourseService;
import aptech.project.educhain.domain.useCases.courses.course.CreateCourseUsecase.CreateCourseParams;
import aptech.project.educhain.domain.useCases.courses.course.CreateCourseUsecase.CreateCourseUsecase;
import aptech.project.educhain.domain.useCases.courses.course.DeleteCourseUsecase.DeleteCourseUsecase;
import aptech.project.educhain.domain.useCases.courses.course.GetCourseDetailUsecase.GetCourseDetailUsecase;
import aptech.project.educhain.domain.useCases.courses.course.GetCoursesByTeacherUsecase.GetCoursesByTeacherParams;
import aptech.project.educhain.domain.useCases.courses.course.GetCoursesByTeacherUsecase.GetCoursesByTeacherUsecase;
import aptech.project.educhain.domain.useCases.courses.course.UpdateCourseUsecase.UpdateCourseParams;
import aptech.project.educhain.domain.useCases.courses.course.UpdateCourseUsecase.UpdateCourseUsecase;

@Service
public class CourseService implements ICourseService {
    @Autowired
    CreateCourseUsecase createCourseUsecase;

    @Autowired
    GetCoursesByTeacherUsecase getCoursesByTeacherUsecase;

    @Autowired
    UpdateCourseUsecase updateCourseUsecase;

    @Autowired
    DeleteCourseUsecase deleteCourseUsecase;

    @Autowired
    GetCourseDetailUsecase getCourseDetailUsecase;

    @Override
    public AppResult<CourseDTO> createCourse(CreateCourseParams params) {
        return createCourseUsecase.execute(params);
    }

    @Override
    public AppResult<Page<CourseDTO>> getCoursesByTeacher(GetCoursesByTeacherParams params) {
        return getCoursesByTeacherUsecase.execute(params);
    }

    @Override
    public AppResult<CourseDTO> getCourseDetail(Integer courseId) {
        return getCourseDetailUsecase.execute(courseId);
    }

    @Override
    public AppResult<CourseDTO> updateCourse(UpdateCourseParams params) {
        return updateCourseUsecase.execute(params);
    }

    @Override
    public AppResult<CourseDTO> deleteCourse(int courseId) {
        return deleteCourseUsecase.execute(courseId);
    }
}
