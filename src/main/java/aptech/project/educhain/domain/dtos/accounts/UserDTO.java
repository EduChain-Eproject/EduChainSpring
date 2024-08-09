package aptech.project.educhain.domain.dtos.accounts;

import java.util.List;

import aptech.project.educhain.data.entities.accounts.Role;
import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import aptech.project.educhain.domain.dtos.courses.AwardDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;
import aptech.project.educhain.domain.dtos.courses.UserHomeworkDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String avatarPath;
    private String phone;
    private String address;
    private Role role;
    private String email;
    private Boolean isActive;

    private List<AwardDTO> userAwardDtos;
    private List<UserHomeworkDTO> userHomeworkDtos;
    private List<UserCourseDTO> courseDtosParticipated;
    private List<UserInterestsDTO> userInterestDtos;

    private long numberOfStudents;
    private CourseDTO mostPopularCourse;
}
