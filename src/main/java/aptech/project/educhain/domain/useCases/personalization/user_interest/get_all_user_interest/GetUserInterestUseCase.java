package aptech.project.educhain.domain.useCases.personalization.user_interest.get_all_user_interest;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.UserInterest;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.accounts.UserInterestRepository;

import aptech.project.educhain.data.repositories.courses.CourseCategoryRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

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
    @Override
    public AppResult<Page<UserInterestsDTO>> execute(GetUserInterestByUserIdParams params) {
        try {
            Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
            Page<UserInterest> userInterestList = userInterestRepository.findByUserId(params.getUser_id(),params.getTitleSearch(), pageable);


            List<UserInterestsDTO> userInterestsDTOList = userInterestList.stream()
                    .map(userInterest -> {
                        Course course = courseRepository.findCourseWithId(userInterest.getCourse().getId());
                        UserInterestsDTO dto = new UserInterestsDTO();
                        dto.setStudent_id(params.getUser_id());
                        dto.setCourse_id(course.getId());
                        dto.setTitle(course.getTitle());
                        dto.setPrice(course.getPrice());
                        dto.setTeacherName(course.getTeacher().getEmail());
                        dto.setDescription(course.getDescription());
                        dto.setCategoryList(courseCategoryRepository.getCategoryByCourseId(course.getId()));
                        return dto;
                    })
                    .toList();
            // Create a Page<UserInterestsDTO> object with mapped data
            return AppResult.successResult(new PageImpl<>(userInterestsDTOList, pageable, userInterestList.getTotalElements()));
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Fail to get user-interestsList"));
        }
    }
}
