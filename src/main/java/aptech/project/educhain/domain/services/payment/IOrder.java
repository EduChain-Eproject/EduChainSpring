package aptech.project.educhain.domain.services.payment;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.payment.Order;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase.AddOrderParams;

import java.util.List;

public interface IOrder {
    AppResult<OrderDTO> addOrder(AddOrderParams params);

    AppResult<List<Order>> getAllOrders();

    AppResult<List<Order>> getOrdersByUserId(int userId);

    AppResult<Order> getOrderById(int orderId);
}
