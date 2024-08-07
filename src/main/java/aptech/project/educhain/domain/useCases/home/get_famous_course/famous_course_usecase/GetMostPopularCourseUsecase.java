package aptech.project.educhain.domain.useCases.home.get_famous_course.famous_course_usecase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.data.repositories.courses.UserCourseRepository;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetMostPopularCourseUsecase implements Usecase<List<CourseDTO>, Void> {

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppResult<List<CourseDTO>> execute(Void params) {
        try {
            List<Course> mostPopularCourse = userCourseRepository.findMostPopularCourse();
            if (!mostPopularCourse.isEmpty()) {
                List<CourseDTO> courseDTOs = mostPopularCourse.stream()
                        .map(this::mapCourseToDTO)
                        .collect(Collectors.toList());
                return AppResult.successResult(courseDTOs);
            } else {
                return AppResult.failureResult(new Failure("No course found"));
            }
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error finding the most popular course: " + e.getMessage()));
        }
    }

    private CategoryDTO mapCategoryToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setCategoryName(category.getCategoryName());
        return categoryDTO;
    }

    public CourseDTO mapCourseToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setPrice(course.getPrice());
        courseDTO.setStatus(course.getStatus().toString());

        List<CategoryDTO> categoryDTOs = course.getCategories().stream()
                .map(this::mapCategoryToDTO)
                .collect(Collectors.toList());
        courseDTO.setCategoryDtos(categoryDTOs);
        return courseDTO;
    }
}
