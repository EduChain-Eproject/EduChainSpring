package aptech.project.educhain.domain.useCases.home.count_student;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.repositories.courses.UserCourseRepository;
import aptech.project.educhain.domain.dtos.home.CountStudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountStudentUseCase implements Usecase<CountStudentDTO,Void> {
    @Autowired
    UserCourseRepository userCourseRepository;

    @Override
    public AppResult<CountStudentDTO> execute(Void params) {
        try{
            Long countNumber =  userCourseRepository.countDistinctStudents();
            CountStudentDTO countStudentDTO = new CountStudentDTO();
            countStudentDTO.setAllStudent(countNumber);
            return AppResult.successResult(countStudentDTO);
        }
        catch (Exception e){
            return AppResult.failureResult(new Failure("cant count student"));
        }
    }
}
