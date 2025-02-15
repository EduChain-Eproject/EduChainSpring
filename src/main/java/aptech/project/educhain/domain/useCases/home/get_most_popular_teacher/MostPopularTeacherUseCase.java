package aptech.project.educhain.domain.useCases.home.get_most_popular_teacher;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.UserCourseRepository;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.home.PopularTeacherDTO;

@Component
public class MostPopularTeacherUseCase implements Usecase<PopularTeacherDTO, Void> {
    @Autowired
    UserCourseRepository userCourseRepository;
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<PopularTeacherDTO> execute(Void params) {
        try {
            List<Course> mostPopularCourse = userCourseRepository.findMostPopularCourse();
            Course course = mostPopularCourse.get(0);
            CourseDTO courseDTO = mapCourseToDTO(mostPopularCourse.get(0));
            long countStudent = userCourseRepository.countDistinctStudentsByCourse(course.getId());
            // long counted = countStudent.size();
            PopularTeacherDTO teacher = new PopularTeacherDTO();
            if (!mostPopularCourse.isEmpty()) {
                Course mostPopular = mostPopularCourse.get(0);
                User user = authUserRepository.findUserWithId(mostPopular.getTeacher().getId());
                teacher.setEmail(user.getEmail());
                teacher.setFirstName(user.getFirstName());
                teacher.setLastName(user.getLastName());
                teacher.setAvatarPath(user.getAvatarPath());
                teacher.setPhone(user.getPhone());
                teacher.setNumberOfStudents(countStudent);
                teacher.setMostPopularCourse(courseDTO);
            }
            return AppResult.successResult(teacher);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error finding the most popular teacher: " + e.getMessage()));
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
