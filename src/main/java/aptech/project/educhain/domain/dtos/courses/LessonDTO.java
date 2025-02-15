package aptech.project.educhain.domain.dtos.courses;

import java.util.List;

import lombok.Data;

@Data
public class LessonDTO {

    private Integer id;
    private String lessonTitle;
    private String description;
    private String videoTitle;
    private String videoURL;

    private ChapterDTO chapterDto;
    private List<HomeworkDTO> homeworkDtos;
    private boolean isCurrentUserFinished;
}
