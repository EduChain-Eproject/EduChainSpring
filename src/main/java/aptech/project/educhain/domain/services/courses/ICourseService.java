package aptech.project.educhain.domain.services.courses;

import java.util.List;

import org.springframework.data.domain.Page;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.useCases.courses.course.CreateCourseUsecase.CreateCourseParams;
import aptech.project.educhain.domain.useCases.courses.course.GetCoursesByTeacherUsecase.GetCoursesByTeacherParams;
import aptech.project.educhain.domain.useCases.courses.course.SearchCoursesUseCase.CourseSearchParams;
import aptech.project.educhain.domain.useCases.courses.course.UpdateCourseUsecase.UpdateCourseParams;

public interface ICourseService {
    public AppResult<CourseDTO> createCourse(CreateCourseParams params);

    AppResult<Page<CourseDTO>> getCoursesByTeacher(GetCoursesByTeacherParams params);

    AppResult<CourseDTO> updateCourse(UpdateCourseParams params);

    AppResult<CourseDTO> deleteCourse(int courseId);

    AppResult<CourseDTO> getCourseDetail(Integer courseId);

    AppResult<Page<CourseDTO>> searchCourses(CourseSearchParams request);

    AppResult<List<CourseDTO>> getRelatedCourses(Integer courseId);
}
