package aptech.project.educhain.endpoint.responses.courses.lesson.teacher;

import java.util.List;

import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import lombok.Data;

@Data
public class GetLessonDetailResponse {
    private Integer id;
    private String lessonTitle;
    private String description;
    private String videoTitle;
    private String videoURL;
    private ChapterDTO chapterDto;
    private List<HomeworkDTO> homeworkDtos;
}