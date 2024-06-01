package aptech.project.educhain.repositories.chats;

import aptech.project.educhain.models.chats.Chat;
import aptech.project.educhain.models.chats.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    public List<Chat> findChatByUserChats(UserChat userChat);
}
