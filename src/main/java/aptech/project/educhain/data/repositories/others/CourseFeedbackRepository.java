package aptech.project.educhain.data.repositories.others;

import aptech.project.educhain.data.entities.chats.Chat;
import aptech.project.educhain.data.entities.others.CourseFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseFeedbackRepository extends JpaRepository<CourseFeedback, Integer>{

}
