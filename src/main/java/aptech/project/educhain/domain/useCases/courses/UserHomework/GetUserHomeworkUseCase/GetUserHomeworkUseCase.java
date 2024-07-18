package aptech.project.educhain.domain.useCases.courses.UserHomework.GetUserHomeworkUseCase;

import java.math.BigDecimal;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.UserHomework;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.UserHomeworkRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import aptech.project.educhain.domain.dtos.courses.UserAnswerDTO;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;

@Component
public class GetUserHomeworkUseCase implements Usecase<UserHomeworkDTO, GetUserHomeworkParams> {

    @Autowired
    UserHomeworkRepository userHomeworkRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<UserHomeworkDTO> execute(GetUserHomeworkParams params) {
        try {
            Optional<UserHomework> userHomeworkOptional = userHomeworkRepository.findByUserIdAndHomeworkId(
                    params.userId,
                    params.homeworkId);

            if (userHomeworkOptional.isPresent()) {

                UserHomework userHomework = userHomeworkOptional.get();

                UserHomeworkDTO userHomeworkDTO = modelMapper.map(userHomework, UserHomeworkDTO.class);

                userHomeworkDTO.setUserAnswerDtos(
                        userHomework.getUserAnswers().stream().map(
                                ua -> {
                                    var userAnswerDto = modelMapper.map(ua, UserAnswerDTO.class);

                                    userAnswerDto.setQuestionId(
                                            modelMapper.map(ua.getQuestion(), QuestionDTO.class).getId());

                                    userAnswerDto.setAnswerId(
                                            modelMapper.map(ua.getAnswer(), AnswerDTO.class).getId());

                                    return userAnswerDto;
                                }).toList());

                return AppResult.successResult(userHomeworkDTO);

            } else {
                UserHomework userHomework = new UserHomework();
                userHomework.setUser(authUserRepository.findUserWithId(params.getUserId()));
                userHomework.setHomework(homeworkRepository.findById(params.getHomeworkId()).get());
                userHomework.setProgress(0.0);
                userHomework.setGrade(BigDecimal.ZERO);
                userHomework.setIsSubmitted(false);

                userHomework = userHomeworkRepository.save(userHomework);

                return AppResult.successResult(modelMapper.map(userHomework, UserHomeworkDTO.class));
            }
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get user homework: " + e.getMessage()));
        }
    }

}
