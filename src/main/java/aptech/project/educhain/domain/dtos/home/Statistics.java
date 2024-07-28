package aptech.project.educhain.domain.dtos.home;

import java.util.List;

import aptech.project.educhain.domain.dtos.courses.CourseFeedbackDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {
    private Long numberOfEnrollments;
    private Long certificationsMade;
    private Integer satisfactionRate;
    private List<CourseFeedbackDTO> bestFeedbacks;
}
