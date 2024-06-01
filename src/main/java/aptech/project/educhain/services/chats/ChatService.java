package aptech.project.educhain.services.chats;

import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.chats.Chat;
import aptech.project.educhain.models.chats.UserChat;
import aptech.project.educhain.repositories.chats.ChatRepository;
import aptech.project.educhain.services.auth.AuthService;
import aptech.project.educhain.services.chats.IChatService.IChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService implements IChat {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    AuthService authService;

    @Autowired
    UserChatService userChatService;

    @Override
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public Chat findOne(Integer id) {
        return chatRepository.findById(id).get();
    }

    @Override
    public List<Chat> findChatByUser(Integer id) {
        User user = authService.findUserById(id);
        List<UserChat> userChats = userChatService.findUserChatByUser(user);
        List<Chat> chats = new ArrayList<>();
        for (UserChat userChat : userChats) {
            chats.add(userChat.getChat());
        }
        return chats;
    }


    @Override
    public Chat create(Chat newChat) {
        try{
            return chatRepository.save(newChat);
        }catch(Exception ex){
            ex.getMessage();
        }
        return null;
    }

    @Override
    public Chat update(Integer id, Chat chat) {
        try{
            Chat ch = findOne(id);
            if (ch != null){
                ch.setName(chat.getName());
                return chatRepository.save(chat);
            }
            return null;
        }catch(Exception ex){
            ex.getMessage();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        Chat ch = findOne(id);
        if(ch != null){
            chatRepository.delete(ch);
            return true;
        }
        return false;
    }
}
