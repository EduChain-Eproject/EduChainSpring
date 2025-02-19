package aptech.project.educhain.data.serviceImpl.paypal;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.data.entities.payment.Order;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import aptech.project.educhain.domain.services.payment.IOrder;
import aptech.project.educhain.domain.useCases.admin.course_list.GetCourseListParams;
import aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase.AddOrderParams;
//import aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase.AddOrderUseCase;
import aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase.AddOrderUseCase;
import aptech.project.educhain.domain.useCases.payment.order.getAllOrderUseCase.GetAllOrderParams;
import aptech.project.educhain.domain.useCases.payment.order.getAllOrderUseCase.GetAllOrderUseCase;
import aptech.project.educhain.domain.useCases.payment.order.getOrderByUserUseCase.GetOrderByUserParams;
import aptech.project.educhain.domain.useCases.payment.order.getOrderByUserUseCase.GetOrderByUserUseCase;
import aptech.project.educhain.domain.useCases.payment.order.getOrderUseCase.GetOrderUseCase;
import aptech.project.educhain.domain.useCases.payment.order.getOrdersByCourseUseCase.GetOrderByCourseUseCase;
import aptech.project.educhain.domain.useCases.payment.order.getOrdersByCourseUseCase.GetOrderCourseParams;
import aptech.project.educhain.endpoint.requests.order.ListOrderByCourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrder {
    private final AddOrderUseCase addOrderUseCase;
    private final GetOrderByUserUseCase getOrderByUserUseCase;
    private final GetOrderByCourseUseCase getOrderByCourseUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final GetAllOrderUseCase getAllOrderUseCase;

    public OrderService(AddOrderUseCase addOrderUseCase, GetOrderByUserUseCase getOrderByUserUseCase, GetOrderByCourseUseCase getOrderByCourseUseCase, GetOrderUseCase getOrderUseCase, GetAllOrderUseCase getAllOrderUseCase) {
        this.addOrderUseCase = addOrderUseCase;
        this.getOrderByUserUseCase = getOrderByUserUseCase;
        this.getOrderByCourseUseCase = getOrderByCourseUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.getAllOrderUseCase = getAllOrderUseCase;
    }

    @Override
    public AppResult<OrderDTO> addOrder(AddOrderParams params) {
        return addOrderUseCase.execute(params);
    }

    @Override
    public AppResult<Page<OrderDTO>> getAllOrders(GetAllOrderParams param) {
        return getAllOrderUseCase.execute(param);
    }

    @Override
    public AppResult<Page<OrderDTO>> getOrdersByUserId(GetOrderByUserParams param) {
        return getOrderByUserUseCase.execute(param);
    }

    @Override
    public AppResult<Page<OrderDTO>> getOrdersByCourseId(GetOrderCourseParams req) {
        return getOrderByCourseUseCase.execute(req);
    }

    @Override
    public AppResult<OrderDTO> getOrderById(int orderId) {
        return getOrderUseCase.execute(orderId);
    }
}
