package aptech.project.educhain.domain.useCases.personalization.user_homework.take_one_userhomework;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.UserHomework;
import aptech.project.educhain.data.repositories.courses.UserHomeworkRepository;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TakeOneUserHomeworkUseCase implements Usecase<UserHomeworkDTO, TakeOneUserHomeworkParams> {

    @Autowired
    private UserHomeworkRepository userHomeworkRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppResult<UserHomeworkDTO> execute(TakeOneUserHomeworkParams params) {
        try {
            // Retrieve the UserHomework entity based on student_id and homework_id
            Optional<UserHomework> userHomeworkOptional = userHomeworkRepository.findByUserIdAndHomeworkId(
                    params.getStudent_id(), params.getHomework_id());

            if (userHomeworkOptional.isEmpty()) {
                return AppResult.failureResult(new Failure("UserHomework not found for the provided student_id and homework_id."));
            }

            UserHomework userHomework = userHomeworkOptional.get();

            // Map UserHomework to UserHomeworkDTO
            UserHomeworkDTO userHomeworkDTO = modelMapper.map(userHomework, UserHomeworkDTO.class);

            // Map nested DTOs
            userHomeworkDTO.setUserDto(modelMapper.map(userHomework.getUser(), UserDTO.class));
            userHomeworkDTO.setHomeworkDto(modelMapper.map(userHomework.getHomework(), HomeworkDTO.class));

            // Map UserAnswerDTOs
            List<UserAnswerDTO> userAnswerDTOs = userHomework.getUserAnswers()
                    .stream()
                    .map(userAnswer -> {
                        UserAnswerDTO userAnswerDTO = modelMapper.map(userAnswer, UserAnswerDTO.class);
                        userAnswerDTO.setQuestionDto(modelMapper.map(userAnswer.getQuestion(), QuestionDTO.class));
                        userAnswerDTO.setAnswerDto(modelMapper.map(userAnswer.getAnswer(), AnswerDTO.class));
                        return userAnswerDTO;
                    })
                    .collect(Collectors.toList());
            userHomeworkDTO.setUserAnswerDtos(userAnswerDTOs);

            return AppResult.successResult(userHomeworkDTO);

        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to retrieve user homework: " + e.getMessage()));
        }
    }
}

