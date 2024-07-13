package aptech.project.educhain.domain.useCases.account.user_interest.delete_from_user_interests;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.UserInterest;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.accounts.UserInterestRepository;
import aptech.project.educhain.data.repositories.courses.CourseCategoryRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserInterestsUseCase implements Usecase<Boolean,DeleteUserInterestsParams> {
    @Autowired
    UserInterestRepository userInterestRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    CourseCategoryRepository courseCategoryRepository;

    @Override
    public AppResult<Boolean> execute(DeleteUserInterestsParams params) {
        try{
            UserInterest userInterest = userInterestRepository.findByCourseIdAndUserId( params.getCourse_id(),params.getStudent_id());
            userInterestRepository.delete(userInterest);
            return AppResult.successResult(true);
        }
        catch (Exception e){
            return AppResult.failureResult(new Failure("Failed to add: " + e.getMessage()));
        }
    }

}
