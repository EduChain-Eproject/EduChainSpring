package aptech.project.educhain.domain.useCases.personalization.user_course.get_all_user_course;

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
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
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
            Page<UserCourse> results = userCourseRepository.findAllWithParams(params.getStudentId(),
                    params.getTitleSearch(), params.getCompletionStatus(), pageable);

            // Convert Page<UserCourse> to Page<UserCourseDTO>
            Page<UserCourseDTO> userCourseDTOs = results.map(uc -> {
                UserCourseDTO dto = new UserCourseDTO();
                dto.setProgress(uc.getProgress());
                dto.setUserDto(modelMapper.map(uc.getUser(), UserDTO.class));
                dto.setCourseDto(modelMapper.map(uc.getCourse(), CourseDTO.class));
                dto.setEnrollmentDate(uc.getEnrollmentDate());
                dto.setCompletionStatus(uc.getCompletionStatus());

                return dto;
            });

            return AppResult.successResult(userCourseDTOs);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get user courses: " + e.getMessage()));
        }
    }
}
