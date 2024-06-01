package aptech.project.educhain.services.chats;

import aptech.project.educhain.models.chats.Message;
import aptech.project.educhain.repositories.chats.MessageRepository;
import aptech.project.educhain.services.chats.IChatService.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements IMessage {
    @Autowired
    MessageRepository messageRepository;

    @Override
    public Message sendMessage(Message newMessage) {
        try{
            return messageRepository.save(newMessage);
        }catch(Exception ex){
            ex.getMessage();
        }
        return null;
    }

    @Override
    public boolean deleteMessage(Integer id) {
        Message msg = messageRepository.findById(id).get();
        if(msg != null){
            messageRepository.delete(msg);
            return true;
        }
        return false;
    }
}
