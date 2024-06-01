package aptech.project.educhain.services.chats.IChatService;

import aptech.project.educhain.models.chats.Chat;

import java.util.List;

public interface IChat {
    List<Chat> findAll();

    Chat findOne(Integer id);

    List<Chat> findChatByUser(Integer id);
    Chat create(Chat newChat);
    Chat update(Integer id, Chat chat);
    boolean delete(Integer id);
}
