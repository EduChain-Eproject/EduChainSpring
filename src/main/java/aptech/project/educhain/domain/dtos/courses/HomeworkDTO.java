package aptech.project.educhain.domain.dtos.courses;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.courses.Lesson;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.entities.courses.UserHomework;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class HomeworkDTO {
    private Integer id;
    private Integer userID;
    private Integer lessonID;
    private String title;
    private String description;
}
