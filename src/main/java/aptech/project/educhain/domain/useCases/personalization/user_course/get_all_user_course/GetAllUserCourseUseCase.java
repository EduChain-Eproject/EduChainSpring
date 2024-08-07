package aptech.project.educhain.domain.useCases.personalization.user_course.get_all_user_course;

import java.util.stream.Collectors;

import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.UserCourse;
import aptech.project.educhain.data.repositories.courses.UserCourseRepository;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;

@Component
public class GetAllUserCourseUseCase implements Usecase<Page<UserCourseDTO>, UserCourseParams> {

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppResult<Page<UserCourseDTO>> execute(UserCourseParams params) {
        try {
            Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
            Page<UserCourse> results = userCourseRepository.findAllWithParams(params.getStudentId(), params.getTitleSearch(), params.getCompletionStatus(), pageable);

            // Convert Page<UserCourse> to Page<UserCourseDTO>
            Page<UserCourseDTO> userCourseDTOs = results.map(uc -> {
                UserCourseDTO dto = new UserCourseDTO();
                dto.setTeacherName(uc.getCourse().getTeacher().getFirstName() + " " + uc.getCourse().getTeacher().getLastName());
                dto.setTeacherEmail(uc.getCourse().getTeacher().getEmail());
                dto.setTitle(uc.getCourse().getTitle());
                dto.setEnrollmentDate(uc.getEnrollmentDate());
                dto.setPrice(uc.getCourse().getPrice());
                dto.setCompletionStatus(uc.getCompletionStatus());
                dto.setCategoryList(uc.getCourse().getCategories().stream()
                        .map(category -> modelMapper.map(category, CategoryDTO.class))
                        .collect(Collectors.toList()));
                return dto;
            });

            return AppResult.successResult(userCourseDTOs);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get user courses: " + e.getMessage()));
        }
    }
}
