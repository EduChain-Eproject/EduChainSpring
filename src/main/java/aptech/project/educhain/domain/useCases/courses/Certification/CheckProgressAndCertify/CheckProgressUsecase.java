package aptech.project.educhain.domain.useCases.courses.Certification.CheckProgressAndCertify;

import java.math.RoundingMode;
import java.math.BigDecimal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Certification;
import aptech.project.educhain.data.entities.courses.CertificationStatus;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.CertificationRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.UserCourseRepository;
import aptech.project.educhain.domain.dtos.courses.CertificationDTO;
import jakarta.transaction.Transactional;

@Component
public class CheckProgressUsecase implements Usecase<CertificationDTO, CheckProgressParams> {
    @Autowired
    UserCourseRepository userCourseRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    CertificationRepository certificationRepository;

    @Override
    @Transactional
    public AppResult<CertificationDTO> execute(CheckProgressParams params) {
        try {
            var currentHomework = homeworkRepository.findById(params.getHomeworkId());

            if (!currentHomework.isPresent()) {
                return AppResult.failureResult(new Failure("No homework found"));
            }

            var currentCourse = currentHomework.get().getLesson().getChapter().getCourse();

            var userCourse = userCourseRepository.findByUserIdAndCourseId(params.getStudentId(), currentCourse.getId());

            if (!userCourse.isPresent()) {
                return AppResult.failureResult(new Failure("No enrollment found"));
            }

            var progress = userCourse.get().getProgress();

            var completedHomworks = userCourseRepository.countHomeworks(params.getStudentId(), currentCourse.getId());
            var submittedHomworks = userCourseRepository.countSubmittedHomeworks(params.getStudentId(),
                    currentCourse.getId());

            var newProgress = calculateProgress(submittedHomworks, completedHomworks);

            if (progress != newProgress) {
                userCourse.get().setProgress(newProgress);
            }

            userCourseRepository.saveAndFlush(userCourse.get());

            CertificationDTO res = null;
            // if (newProgress.equals(BigDecimal.valueOf(100))) {
            if (true) {
                var certification = new Certification();

                var user = authUserRepository.findUserWithId(params.getStudentId());
                certification.setUser(user);

                var course = courseRepository.findCourseWithId(currentCourse.getId());
                certification.setCourse(course);

                certification.setStatus(CertificationStatus.PENDING);

                certificationRepository.save(certification);

                res = modelMapper.map(certification, CertificationDTO.class);
            }

            return AppResult.successResult(res);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Fail to update progress"));
        }

    }

    private BigDecimal calculateProgress(Long submitted, Long total) {
        if (total == 0) {
            return BigDecimal.ZERO; // Avoid division by zero
        }

        BigDecimal submittedDecimal = BigDecimal.valueOf(submitted);
        BigDecimal totalDecimal = BigDecimal.valueOf(total);

        BigDecimal progress = submittedDecimal
                .divide(totalDecimal, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return progress;
    }
}
