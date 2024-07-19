package aptech.project.educhain.endpoint.responses.courses.homework;

import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetHomeworkAndUserHomeworkResponse {
    private HomeworkDTO homeworkDto;
    private UserHomeworkDTO userHomeworkDto;
    private AwardDTO awardDto;
}
