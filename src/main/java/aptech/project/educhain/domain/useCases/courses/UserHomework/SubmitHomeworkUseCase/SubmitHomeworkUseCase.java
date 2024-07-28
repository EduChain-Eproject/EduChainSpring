package aptech.project.educhain.domain.useCases.courses.UserHomework.SubmitHomeworkUseCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Award;
import aptech.project.educhain.data.entities.courses.AwardStatus;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.entities.courses.UserAnswer;
import aptech.project.educhain.data.entities.courses.UserHomework;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.AnswerRepository;
import aptech.project.educhain.data.repositories.courses.AwardRepository;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.data.repositories.courses.UserAnswerRepository;
import aptech.project.educhain.data.repositories.courses.UserHomeworkRepository;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import aptech.project.educhain.endpoint.responses.courses.homework.SubmitHomeworkResponse;

@Component
public class SubmitHomeworkUseCase implements Usecase<SubmitHomeworkResponse, SubmitHomeworkParams> {
    @Autowired
    UserHomeworkRepository userHomeworkRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    UserAnswerRepository userAnswerRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<SubmitHomeworkResponse> execute(SubmitHomeworkParams params) {
        try {
            Optional<UserHomework> userHomeworkOptional = userHomeworkRepository
                    .findByUserIdAndHomeworkId(params.getUserId(), params.getHomeworkId());
            if (!userHomeworkOptional.isPresent()) {
                return AppResult.failureResult(new Failure("User homework not found"));
            }

            UserHomework userHomework = userHomeworkOptional.get();
            List<Question> questions = userHomework.getHomework().getQuestions();
            List<UserAnswer> userAnswers = userHomework.getUserAnswers();

            if (questions.size() == 0 || questions.size() != userAnswers.size()) {
                return AppResult.failureResult(new Failure("Not all questions are answered"));
            }

            // Calculate grade
            int totalQuestions = questions.size();
            int correctAnswers = (int) userAnswers.stream()
                    .filter(userAnswer -> userAnswer.getAnswer().getId().equals(
                            userAnswer.getQuestion().getCorrectAnswer().getId()))
                    .count();
            BigDecimal grade = BigDecimal.valueOf((double) correctAnswers / totalQuestions * 100);

            userHomework.setProgress(100.0);
            userHomework.setIsSubmitted(true);
            userHomework.setGrade(grade);
            userHomeworkRepository.save(userHomework);

            // Create award
            Award award = new Award();
            award.setUser(authUserRepository.findById(params.getUserId()).get());
            award.setHomework(userHomework.getHomework());
            award.setStatus(AwardStatus.PENDING);
            award.setSubmissionDate(LocalDateTime.now());
            award = awardRepository.save(award);

            SubmitHomeworkResponse res = new SubmitHomeworkResponse(
                    modelMapper.map(userHomework, UserHomeworkDTO.class),
                    modelMapper.map(award, AwardDTO.class));

            return AppResult.successResult(res);

        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to submit homework: " + e.getMessage()));
        }
    }

}
