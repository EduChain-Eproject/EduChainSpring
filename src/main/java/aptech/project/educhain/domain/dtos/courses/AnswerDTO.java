package aptech.project.educhain.domain.dtos.courses;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.entities.courses.UserAnswer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class AnswerDTO{
    private Integer id;

    private Integer question;

    private String answerText;
}
