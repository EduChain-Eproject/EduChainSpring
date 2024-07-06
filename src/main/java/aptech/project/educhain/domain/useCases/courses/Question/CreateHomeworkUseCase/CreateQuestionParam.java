package aptech.project.educhain.domain.useCases.courses.Question.CreateHomeworkUseCase;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Lesson;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateQuestionParam {
    private int homeworkId;
    private int userId;
    private String questionText;
    private List<Answers> answers;
    private Answers correctAnswer;
}
