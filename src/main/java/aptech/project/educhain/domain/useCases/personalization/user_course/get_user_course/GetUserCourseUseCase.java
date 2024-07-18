package aptech.project.educhain.domain.useCases.personalization.user_course.get_user_course;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.repositories.courses.UserCourseRepository;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;

@Component
public class GetUserCourseUseCase implements Usecase<UserCourseDTO, GetUserCourseParams> {

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppResult<UserCourseDTO> execute(GetUserCourseParams params) {
        try {
            var result = userCourseRepository.findByUserIdAndCourseId(params.getUserId(), params.getCourseId());

            if (result.isEmpty()) {
                return AppResult.failureResult(null);
            }

            return AppResult.successResult(modelMapper.map(result.get(), UserCourseDTO.class));

        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Fail to get user course"));
        }
    }

}
