package aptech.project.educhain.data.repositories.chats;

import aptech.project.educhain.data.entities.chats.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer>{

}
