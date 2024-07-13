package aptech.project.educhain.domain.useCases.courses.Homework.CreateHomeworkUseCase;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Lesson;
import lombok.Data;

@Data
public class CreateHomeworkParam {
    private int userId;
    private int lessonId;
    private String title;
    private String description;
}
