package aptech.project.educhain.endpoint.responses.courses.lesson.teacher;

import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import lombok.Data;

@Data
public class GetLessonDetailResponse {
    private Integer id;
    private String lessonTitle;
    private String description;
    private String videoTitle;
    private String videoURL;
    private ChapterDTO chapterDto;
}