package aptech.project.educhain.data.repositories.courses;

import aptech.project.educhain.data.entities.chats.Chat;
import aptech.project.educhain.data.entities.courses.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkRepository extends JpaRepository<Homework, Integer>{

}
