package aptech.project.educhain.data.repositories.others;

import aptech.project.educhain.data.entities.chats.Chat;
import aptech.project.educhain.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
