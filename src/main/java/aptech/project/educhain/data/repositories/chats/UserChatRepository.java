package aptech.project.educhain.data.repositories.chats;

import aptech.project.educhain.data.entities.chats.Message;
import aptech.project.educhain.data.entities.chats.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRepository extends JpaRepository<UserChat, Integer> {
}
