package aptech.project.educhain.repositories.chats;

import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.chats.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChatRepository extends JpaRepository<UserChat, Integer> {
    public List<UserChat> findUserChatByUser(User user);
}
