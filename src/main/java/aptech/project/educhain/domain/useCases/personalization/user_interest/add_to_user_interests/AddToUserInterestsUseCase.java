package aptech.project.educhain.domain.useCases.personalization.user_interest.add_to_user_interests;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.accounts.UserInterest;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.accounts.UserInterestRepository;
import aptech.project.educhain.data.repositories.courses.CourseCategoryRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import jakarta.transaction.Transactional;

@Component
public class AddToUserInterestsUseCase implements Usecase<UserInterestsDTO, AddToUserInterestsParams> {

    @Autowired
    UserInterestRepository userWishListRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    CourseCategoryRepository courseCategoryRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public AppResult<UserInterestsDTO> execute(AddToUserInterestsParams params) {
        try {

            User findUser = authUserRepository.findUserWithId(params.getStudent_id());
            Course findCourse = courseRepository.findById(params.getCourse_id()).get();

            UserInterest userInterest = new UserInterest();
            userInterest.setCourse(findCourse);
            userInterest.setUser(findUser);

            // TODO: 
            userWishListRepository.save(userInterest);

            UserInterestsDTO wishListDTO = new UserInterestsDTO();
            wishListDTO.setUserDto(modelMapper.map(userInterest.getUser(), UserDTO.class));
            wishListDTO.setCourseDto(modelMapper.map(userInterest.getCourse(), CourseDTO.class));

            return AppResult.successResult(wishListDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to add: " + e.getMessage()));
        }
    }
}
