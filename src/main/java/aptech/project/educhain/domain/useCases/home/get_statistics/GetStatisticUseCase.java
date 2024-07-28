package aptech.project.educhain.domain.useCases.home.get_statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.repositories.courses.UserCourseRepository;
import aptech.project.educhain.domain.dtos.home.Statistics;

@Component
public class GetStatisticUseCase implements Usecase<Statistics, Void> {
    @Autowired
    UserCourseRepository userCourseRepository;

    @Override
    public AppResult<Statistics> execute(Void params) {
        try {
            Long countNumber = userCourseRepository.countDistinctStudents();
            Statistics statistics = new Statistics();
            statistics.setNumberOfEnrollments(countNumber);
            return AppResult.successResult(statistics);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("cant count student"));
        }
    }
}
