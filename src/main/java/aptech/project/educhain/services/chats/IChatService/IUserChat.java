package aptech.project.educhain.services.chats.IChatService;

import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.chats.UserChat;

import java.util.List;

public interface IUserChat {
    List<UserChat> findUserChatByUser(User user);
    UserChat addNewUser(UserChat newUserChat);

    boolean removeMember(Integer id);
}
