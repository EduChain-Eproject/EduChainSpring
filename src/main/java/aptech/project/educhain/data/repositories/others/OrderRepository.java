package aptech.project.educhain.data.repositories.others;

import org.springframework.data.jpa.repository.JpaRepository;

import aptech.project.educhain.data.entities.others.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
