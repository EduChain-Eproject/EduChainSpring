package aptech.project.educhain.endpoint.responses.courses.chapter.teacher;

import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import lombok.Data;

@Data
public class UpdateChapterResponse {
    private Integer id;
    private String chapterTitle;
    private CourseDTO courseDto;
}