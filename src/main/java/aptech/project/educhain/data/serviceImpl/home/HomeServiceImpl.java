package aptech.project.educhain.data.serviceImpl.home;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.home.CountStudentDTO;
import aptech.project.educhain.domain.dtos.home.PopularTeacherDTO;
import aptech.project.educhain.domain.services.home.HomeService;
import aptech.project.educhain.domain.useCases.home.count_student.CountStudentUseCase;
import aptech.project.educhain.domain.useCases.home.get_famous_course.famous_course_usecase.GetMostPopularCourseUsecase;
import aptech.project.educhain.domain.useCases.home.get_most_category.GetMostCategoryParams;
import aptech.project.educhain.domain.useCases.home.get_most_category.GetMostCategoryUseCase;
import aptech.project.educhain.domain.useCases.home.get_most_popular_teacher.MostPopularTeacherUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private GetMostPopularCourseUsecase getMostPopularCourseUsecase;
    @Autowired
    private GetMostCategoryUseCase getMostCategoryUseCase;
    @Autowired
    private MostPopularTeacherUseCase mostPopularTeacherUseCase;
    @Autowired
    private CountStudentUseCase countStudentUseCase;
    @Override
    public AppResult<List<CourseDTO>> getMostPopularCourse() {
        return getMostPopularCourseUsecase.execute(null);
    }

    @Override
    public AppResult<List<CategoryDTO>> getMostPopularCategories(GetMostCategoryParams params){
        return getMostCategoryUseCase.execute(params);
    }

    @Override
    public AppResult<PopularTeacherDTO> getMostPopularTeacher( ){
        return mostPopularTeacherUseCase.execute(null);
    }

    @Override
    public AppResult<CountStudentDTO> countAllStudent(){
        return countStudentUseCase.execute(null);
    }
}
