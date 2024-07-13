package aptech.project.educhain.domain.useCases.courses.Homework.UpdateHomeworkUseCase;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Lesson;
import lombok.Data;

@Data
public class UpdateHomeworkParam {
    private int id;
    private String title;
    private String description;
}
