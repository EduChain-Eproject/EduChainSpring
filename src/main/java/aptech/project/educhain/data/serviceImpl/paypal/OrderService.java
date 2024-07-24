package aptech.project.educhain.data.serviceImpl.paypal;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.payment.Order;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import aptech.project.educhain.domain.services.payment.IOrder;
import aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase.AddOrderParams;
//import aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase.AddOrderUseCase;
import aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase.AddOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrder {
    @Autowired
    private AddOrderUseCase addOrderUseCase;

    @Override
    public AppResult<OrderDTO> addOrder(AddOrderParams params) {
        return addOrderUseCase.execute(params);
    }

    @Override
    public AppResult<List<Order>> getAllOrders() {
        return null;
    }

    @Override
    public AppResult<List<Order>> getOrdersByUserId(int userId) {
        return null;
    }

    @Override
    public AppResult<Order> getOrderById(int orderId) {
        return null;
    }
}
