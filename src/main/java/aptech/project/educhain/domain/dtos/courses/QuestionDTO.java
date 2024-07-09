package aptech.project.educhain.domain.dtos.courses;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.UserAnswer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class QuestionDTO{
    private Integer id;

    private Integer homeworkId;

    private String questionText;

    private List<AnswerDTO> answers;

    private Integer correctAnswerId;
}
