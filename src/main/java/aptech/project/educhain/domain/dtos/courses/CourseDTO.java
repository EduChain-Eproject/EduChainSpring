package aptech.project.educhain.domain.dtos.courses;

import java.util.List;

import lombok.Data;

@Data
public class CourseDTO {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private String status;

    private List<CategoryDTO> categoryDtos;

    // private UserDTO teacherDto;

    private List<ChapterDTO> chapterDtos;

    // private List<UserCourseDto> participatedUserDtos;

    // private List<UserInterestDto> userInterestDtos;

    // private List<CourseFeedbackDto> courseFeedbackDtos;
}