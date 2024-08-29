package aptech.project.educhain.domain.useCases.payment.order.getOrdersByCourseUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.payment.Order;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.data.repositories.payment.OrderRepository;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import aptech.project.educhain.endpoint.requests.order.ListOrderByCourseRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class GetOrderByCourseUseCase implements Usecase<Page<OrderDTO>, GetOrderCourseParams> {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CourseRepository courseRepository;
    @Override
    public AppResult<Page<OrderDTO>> execute(GetOrderCourseParams req) {
        try {
            Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
            Page<Order> ordersPage = orderRepository.findOrdersByCourseIdAndTitleSearch(req.getCourseId(), req.getTitleSearch(), pageable);

            List<OrderDTO> orderDTOs = ordersPage.stream().map(order -> {
                OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
                orderDTO.setUserId(order.getUser().getId());
                orderDTO.setCourseId(order.getCourse().getId());
                return orderDTO;
            }).collect(Collectors.toList());

            Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOs, pageable, ordersPage.getTotalElements());

            return AppResult.successResult(orderDTOPage);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get orders: " + e.getMessage()));
        }
    }

}
