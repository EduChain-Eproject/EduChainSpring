package aptech.project.educhain.domain.dtos.courses;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.courses.Lesson;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.entities.courses.UserHomework;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class HomeworkDTO {
    private Lesson lesson;

    private String title;

    private String description;
}
