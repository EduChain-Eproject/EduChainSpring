package aptech.project.educhain.data.repositories.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import aptech.project.educhain.data.entities.payment.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
