package aptech.project.educhain.domain.dtos.courses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDTO {
    private Integer id;
    private String chapterTitle;

    private CourseDTO courseDto;
    private List<LessonDTO> lessonDtos;
}
