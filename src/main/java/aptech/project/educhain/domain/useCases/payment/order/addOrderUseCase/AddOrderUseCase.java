package aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase;

import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Lesson;
import aptech.project.educhain.data.entities.payment.Order;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.data.repositories.payment.OrderRepository;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.Usecase;

import java.math.BigDecimal;
import java.util.Optional;


@Component
public class AddOrderUseCase implements Usecase<OrderDTO, AddOrderParams> {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public AppResult<OrderDTO> execute(AddOrderParams params) {
        try {
            User user = authUserRepository.findUserWithId(params.getUserId());
            if (user == null) {
                return AppResult.failureResult(new Failure("User not found with ID: " + params.getUserId()));
            }

            Course course = courseRepository.findCourseWithId(params.getCourseId());
            if (course == null) {
                return AppResult.failureResult(new Failure("Course not found with ID: " + params.getCourseId()));
            }

            Order order = new Order();
            order.setAmount(BigDecimal.valueOf(course.getPrice()));
            order .setUser(user);
            order.setCourse(course);

            orderRepository.save(order);

            Order saveOrder = orderRepository.save(order);
            OrderDTO orderDTO = modelMapper.map(saveOrder, OrderDTO.class);
            return AppResult.successResult(orderDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to create order: " + e.getMessage()));
        }
    }
}
