package aptech.project.educhain.services.chats.IChatService;

import aptech.project.educhain.models.chats.Message;

public interface IMessage {
    Message sendMessage(Message newMessage);

    boolean deleteMessage(Integer id);
}
