package aptech.project.educhain.endpoint.responses.home;

import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MostPopularTeacherResponse {
    private String firstName;
    private String lastName;
    private String phone;
    private String teacherEmail;
    private String avatarPath;
    private long studentParticipating;
    private CourseDTO mostPopularCourse;
}
