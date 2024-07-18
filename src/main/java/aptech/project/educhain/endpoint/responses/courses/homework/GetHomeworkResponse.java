package aptech.project.educhain.endpoint.responses.courses.homework;

import java.util.List;

import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import lombok.Data;

@Data
public class GetHomeworkResponse {
    private Integer id;
    private Integer lessonID;
    private LessonDTO lessonDto;
    private String title;
    private String description;
    private List<QuestionDTO> questionDtos;
    private List<AwardDTO> userAwardDtos;
}
