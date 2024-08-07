package aptech.project.educhain.domain.useCases.payment.order.getOrdersByCourseUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.payment.Order;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.data.repositories.payment.OrderRepository;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class GetOrderByCourseUseCase implements Usecase<List<OrderDTO>, Integer> {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CourseRepository courseRepository;
    @Override
    public AppResult<List<OrderDTO>> execute(Integer id) {
        try {
            Course course = courseRepository.findCourseWithId(id);
            List<Order> orders = orderRepository.findOrderByCourse(course);
            List<OrderDTO> orderDTOs = orders.stream().map(order -> {
                OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
                orderDTO.setUserId(order.getUser().getId());
                orderDTO.setCourseId(order.getCourse().getId());
                return orderDTO;
            }).toList();
            return AppResult.successResult(orderDTOs);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get orders: " + e.getMessage()));
        }
    }
}
