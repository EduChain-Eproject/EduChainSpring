package aptech.project.educhain.endpoint.responses.courses.chapter.teacher;

import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChapterResponse {
    private Integer id;
    private CourseDTO courseDto;
    private String chapterTitle;
}