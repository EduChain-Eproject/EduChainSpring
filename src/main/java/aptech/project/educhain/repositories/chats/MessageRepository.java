package aptech.project.educhain.repositories.chats;

import aptech.project.educhain.models.chats.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
