package aptech.project.educhain.data.repositories.others;

import aptech.project.educhain.data.entities.chats.Chat;
import aptech.project.educhain.data.entities.others.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer>{

}
