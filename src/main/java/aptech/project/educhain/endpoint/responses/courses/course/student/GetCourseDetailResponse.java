package aptech.project.educhain.endpoint.responses.courses.course.student;

import java.util.List;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.courses.CourseFeedbackDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCourseDetailResponse {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private String status;

    private UserDTO teacherDto;
    private List<ChapterDTO> chapterDtos;
    private List<CourseFeedbackDTO> courseFeedbackDtos;

    private int numberOfEnrolledStudents;
    private List<CourseDTO> relatedCourseDtos;
}
