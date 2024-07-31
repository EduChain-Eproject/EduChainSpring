package aptech.project.educhain.domain.services.payment;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.data.entities.payment.Order;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase.AddOrderParams;
import aptech.project.educhain.domain.useCases.payment.order.getAllOrderUseCase.GetAllOrderParams;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrder {
    AppResult<OrderDTO> addOrder(AddOrderParams params);

    AppResult<Page<OrderDTO>> getAllOrders(GetAllOrderParams param);

    AppResult<List<OrderDTO>> getOrdersByUserId(int userId);

    AppResult<List<OrderDTO>> getOrdersByCourseId(int courseId);

    AppResult<OrderDTO> getOrderById(int orderId);
}
