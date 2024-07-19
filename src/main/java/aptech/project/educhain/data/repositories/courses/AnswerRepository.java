package aptech.project.educhain.data.repositories.courses;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import aptech.project.educhain.data.entities.courses.Answer;
import aptech.project.educhain.data.entities.courses.Question;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    public List<Answer> getAnswersByQuestion(Question question);
}
