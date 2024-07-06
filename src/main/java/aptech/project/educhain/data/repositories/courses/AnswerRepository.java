package aptech.project.educhain.data.repositories.courses;

import aptech.project.educhain.data.entities.chats.Chat;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answers, Integer>{
    public List<Answers> getAnswersByQuestion(Question question);
}
