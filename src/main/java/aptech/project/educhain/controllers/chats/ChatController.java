package aptech.project.educhain.controllers.chats;

import aptech.project.educhain.models.chats.Chat;
import aptech.project.educhain.models.chats.Message;
import aptech.project.educhain.models.chats.UserChat;
import aptech.project.educhain.services.chats.ChatService;
import aptech.project.educhain.services.chats.MessageService;
import aptech.project.educhain.services.chats.UserChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserChatService userChatService;

    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message send(Message newMessage){
        return messageService.sendMessage(newMessage);
    }

    @MessageMapping("/chat/new")
    public void newChat(Chat chat){
        Chat newChat = chatService.create(chat);
        template.convertAndSend("/topic/chats", newChat);
    }

    @MessageMapping("/chat/user")
    public void addUserToChat(UserChat userChat) {
        UserChat newUserChat = userChatService.addNewUser(userChat);
        template.convertAndSend("/topic/users", newUserChat);
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            System.out.println("User Disconnected : " + username);
        }
    }
}
