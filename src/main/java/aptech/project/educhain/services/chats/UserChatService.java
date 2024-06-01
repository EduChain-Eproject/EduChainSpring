package aptech.project.educhain.services.chats;

import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.chats.UserChat;
import aptech.project.educhain.repositories.chats.UserChatRepository;
import aptech.project.educhain.services.chats.IChatService.IUserChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserChatService implements IUserChat {
    @Autowired
    UserChatRepository userChatRepository;

    @Override
    public List<UserChat> findUserChatByUser(User user) {
        return userChatRepository.findUserChatByUser(user);
    }

    @Override
    public UserChat addNewUser(UserChat newUserChat) {
        try{
            return userChatRepository.save(newUserChat);
        }catch(Exception ex){
            ex.getMessage();
        }
        return null;
    }

    @Override
    public boolean removeMember(Integer id) {
        try{
            UserChat us = userChatRepository.findById(id).get();
            userChatRepository.delete(us);
            return true;
        }catch(Exception ex){
            ex.getMessage();
        }
        return false;
    }
}
