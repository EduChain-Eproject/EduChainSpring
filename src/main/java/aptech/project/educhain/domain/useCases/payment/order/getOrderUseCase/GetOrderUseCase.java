package aptech.project.educhain.domain.useCases.payment.order.getOrderUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.payment.Order;
import aptech.project.educhain.data.repositories.payment.OrderRepository;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetOrderUseCase implements Usecase<OrderDTO, Integer> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public AppResult<OrderDTO> execute(Integer id) {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(id);
            if (optionalOrder.isEmpty()) {
                return AppResult.failureResult(new Failure("Order not found with ID: " + id));
            }

            Order order = optionalOrder.get();
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            orderDTO.setUserId(order.getUser().getId());
            orderDTO.setCourseId(order.getCourse().getId());
            return AppResult.successResult(orderDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get orders: " + e.getMessage()));
        }
    }
}