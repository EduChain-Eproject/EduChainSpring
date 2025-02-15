package aptech.project.educhain.domain.useCases.admin.course_list;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.courses.UserCourse;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class GetCourseListUsecase implements Usecase<Page<CourseDTO>,GetCourseListParams> {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppResult<Page<CourseDTO>> execute(GetCourseListParams params) {
        try {
            Pageable pageable = PageRequest.of(params.getPage(), params.getSize());

            Page<Course> coursesPage = courseRepository.searchByTitle(params.getTitleSearch(),pageable);
            List<CourseDTO> courseDtoList = coursesPage.getContent().stream().map(
                    course -> {
                        CourseDTO courseDTO = modelMapper.map(course,CourseDTO.class);
                        if(course.getTeacher() != null){
                            UserDTO userDTO = modelMapper.map(course.getTeacher(),UserDTO.class);
                            courseDTO.setTeacherDto(userDTO);
                        }
                        List<UserCourseDTO> userCourseDtoList = course.getParticipatedUsers().stream().map(
                                uc -> {
                                    UserCourseDTO userCourseDTO = modelMapper.map(uc,UserCourseDTO.class);
                                    return userCourseDTO;
                                }
                        ).toList();
                        courseDTO.setParticipatedUserDtos(userCourseDtoList);
                        List<CategoryDTO> categoryDtoList = course.getCategories().stream().map(
                                cate -> {
                                    CategoryDTO categoryDto = modelMapper.map(cate, CategoryDTO.class);
                                    return categoryDto;
                                }
                        ).collect(Collectors.toList());
                        courseDTO.setCategoryDtos(categoryDtoList);
                        return courseDTO;
                    }
            ).toList();
            PageImpl<CourseDTO> courseDtoPage = new PageImpl<>(
                    courseDtoList,
                    pageable,
                    coursesPage.getTotalElements()
            );
            return AppResult.successResult(courseDtoPage);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get courses: " + e.getMessage()));
        }
    }
}
