package aptech.project.educhain.data.repositories.courses;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import aptech.project.educhain.data.entities.courses.UserAnswer;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
    @Query("SELECT ua FROM UserAnswer ua WHERE ua.user.id = :userId AND ua.question.id = :questionId")
    Optional<UserAnswer> findByUserIdAndQuestionId(Integer userId, Integer questionId);
}
