package aptech.project.educhain.endpoint.responses.courses.chapter.teacher;

import java.util.List;

import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;
import lombok.Data;

@Data
public class GetChapterDetailResponse {
    private Integer id;
    private String chapterTitle;
    private Integer courseId;
    private CourseDTO courseDto;
    private List<LessonDTO> lessonDtos;
}
