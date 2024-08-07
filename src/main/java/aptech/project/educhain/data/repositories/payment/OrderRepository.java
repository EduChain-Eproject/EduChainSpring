package aptech.project.educhain.data.repositories.payment;

import aptech.project.educhain.data.entities.accounts.User;
import org.springframework.data.jpa.repository.JpaRepository;

import aptech.project.educhain.data.entities.payment.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrderByUser(User user);

    List<Order> findOrderByCourse(Course course);
}
