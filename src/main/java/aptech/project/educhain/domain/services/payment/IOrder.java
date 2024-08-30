package aptech.project.educhain.domain.services.payment;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.data.entities.payment.Order;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase.AddOrderParams;
import aptech.project.educhain.domain.useCases.payment.order.getAllOrderUseCase.GetAllOrderParams;
import aptech.project.educhain.domain.useCases.payment.order.getOrderByUserUseCase.GetOrderByUserParams;
import aptech.project.educhain.domain.useCases.payment.order.getOrdersByCourseUseCase.GetOrderCourseParams;
import aptech.project.educhain.endpoint.requests.order.ListOrderByCourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrder {
    AppResult<OrderDTO> addOrder(AddOrderParams params);

    AppResult<Page<OrderDTO>> getAllOrders(GetAllOrderParams param);

    AppResult<Page<OrderDTO>> getOrdersByUserId(GetOrderByUserParams param);

    AppResult<Page<OrderDTO>> getOrdersByCourseId(GetOrderCourseParams req);

    AppResult<OrderDTO> getOrderById(int orderId);
}
