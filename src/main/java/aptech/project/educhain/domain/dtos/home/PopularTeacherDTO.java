package aptech.project.educhain.domain.dtos.home;

import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularTeacherDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String teacherEmail;
    private String avatarPath;
    private long studentParticipating;
    private CourseDTO mostPopularCourse;
}
