package aptech.project.educhain.domain.useCases.payment.order.getAllOrderUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.payment.Order;
import aptech.project.educhain.data.repositories.payment.OrderRepository;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllOrderUseCase implements Usecase<Page<OrderDTO>, GetAllOrderParams> {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public AppResult<Page<OrderDTO>> execute(GetAllOrderParams params) {
        try {
            Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), Sort.by(params.getSortBy()));
            Page<Order> ordersPage = orderRepository.findAll(pageable);
            Page<OrderDTO> orderDTOsPage = ordersPage.map(order -> {
                OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
                orderDTO.setUserId(order.getUser().getId());
                orderDTO.setCourseId(order.getCourse().getId());
                return orderDTO;
            });
            return AppResult.successResult(orderDTOsPage);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get all orders: " + e.getMessage()));
        }
    }
}
