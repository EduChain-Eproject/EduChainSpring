package aptech.project.educhain.domain.dtos.courses;

import java.util.List;

import aptech.project.educhain.domain.dtos.UserInterests.UserInterestsDTO;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.Data;

@Data
public class CourseDTO {

    private Integer id;
    private String title;
    private String description;
    private Double price;
    private String status;
    private String avatarPath;

    private UserDTO teacherDto;
    private List<CategoryDTO> categoryDtos;
    private List<ChapterDTO> chapterDtos;
    private List<UserCourseDTO> participatedUserDtos;
    private List<UserInterestsDTO> userInterestDtos;
    private List<CourseFeedbackDTO> courseFeedbackDtos;

    private int numberOfEnrolledStudents;
    private UserCourseDTO currentUserCourse;
    private List<CourseDTO> relatedCourseDtos;
    private Integer numberOfLessons;
    private boolean isCurrentUserInterested;
    private Integer lessonIdTolearn;
}
