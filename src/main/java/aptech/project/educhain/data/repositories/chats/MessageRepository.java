package aptech.project.educhain.data.repositories.chats;

import aptech.project.educhain.data.entities.chats.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
