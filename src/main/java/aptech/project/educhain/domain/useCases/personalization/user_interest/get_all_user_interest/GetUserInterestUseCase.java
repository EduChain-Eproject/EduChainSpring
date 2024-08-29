package aptech.project.educhain.domain.useCases.personalization.user_interest.get_all_user_interest;

import java.util.List;

import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.data.entities.accounts.UserInterest;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.accounts.UserInterestRepository;
import aptech.project.educhain.data.repositories.courses.CourseCategoryRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;

@Component
public class GetUserInterestUseCase implements Usecase<Page<UserInterestsDTO>, GetUserInterestByUserIdParams> {
    @Autowired
    UserInterestRepository userInterestRepository;
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseCategoryRepository courseCategoryRepository;
    @Autowired
    ModelMapper modelMapper;
  
    @Override
    public AppResult<Page<UserInterestsDTO>> execute(GetUserInterestByUserIdParams params) {
        try {
            Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
            Page<UserInterest> userInterestList = userInterestRepository.findByUserId(params.getUser_id(),
                    params.getTitleSearch(), pageable);

            List<UserInterestsDTO> userInterestsDTOList = userInterestList.stream()
                    .map(userInterest -> {
                        UserInterestsDTO dto = new UserInterestsDTO();

                        // Map UserDTO
                        dto.setUserDto(modelMapper.map(userInterest.getUser(), UserDTO.class));

                        // Map CourseDTO including teacherDto
                        CourseDTO courseDto = modelMapper.map(userInterest.getCourse(), CourseDTO.class);

                        // Map teacherDto into courseDto
                        if (userInterest.getCourse().getTeacher() != null) {
                            UserDTO teacherDto = modelMapper.map(userInterest.getCourse().getTeacher(), UserDTO.class);
                            courseDto.setTeacherDto(teacherDto);
                        }

                        // Set the courseDto with teacherDto in the user interest DTO
                        dto.setCourseDto(courseDto);

                        return dto;
                    })
                    .toList();

            // Create a Page<UserInterestsDTO> object with mapped data
            return AppResult
                    .successResult(new PageImpl<>(userInterestsDTOList, pageable, userInterestList.getTotalElements()));
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Fail to get user-interestsList"));
        }
    }
}
