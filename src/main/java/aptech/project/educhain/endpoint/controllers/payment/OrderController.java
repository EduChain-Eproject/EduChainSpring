package aptech.project.educhain.endpoint.controllers.payment;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.usecase.NoParam;
import aptech.project.educhain.data.serviceImpl.paypal.OrderService;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import aptech.project.educhain.domain.useCases.courses.course.SearchCoursesUseCase.CourseSearchParams;
import aptech.project.educhain.domain.useCases.payment.order.getAllOrderUseCase.GetAllOrderParams;
import aptech.project.educhain.domain.useCases.payment.order.getOrderByUserUseCase.GetOrderByUserParams;
import aptech.project.educhain.domain.useCases.payment.order.getOrdersByCourseUseCase.GetOrderCourseParams;
import aptech.project.educhain.endpoint.requests.order.ListOrderByCourseRequest;
import aptech.project.educhain.endpoint.requests.order.ListOrderByUserRequest;
import aptech.project.educhain.endpoint.requests.payment.order.OrderRequest;
import aptech.project.educhain.endpoint.responses.courses.question.GetQuestionResponse;
import aptech.project.educhain.endpoint.responses.payment.order.GetOrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Get all orders")
    @PostMapping("")
    public ResponseEntity<?> getAllOrders(@RequestBody OrderRequest request) {
        var params = modelMapper.map(request, GetAllOrderParams.class);

        AppResult<Page<OrderDTO>> result = orderService.getAllOrders(params);
        if (result.isSuccess()) {
            Page<OrderDTO> orderDTOPage = result.getSuccess();
            List<GetOrderResponse> getOrderResponses = orderDTOPage.getContent()
                    .stream()
                    .map(orderDTO -> modelMapper.map(orderDTO, GetOrderResponse.class))
                    .collect(Collectors.toList());

            Page<GetOrderResponse> responsePage = new PageImpl<>(
                    getOrderResponses,
                    PageRequest.of(request.getPage(), request.getSize(), Sort.by(request.getSortBy())),
                    orderDTOPage.getTotalElements());

            return ResponseEntity.ok().body(responsePage);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }


    @Operation(summary = "Get 1 order")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Integer id) {
        AppResult<OrderDTO> result = orderService.getOrderById(id);
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), GetOrderResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @Operation(summary = "Get orders by user")
    @PostMapping("/user")
    public ResponseEntity<?> getOrdersByUser(@RequestBody ListOrderByUserRequest req) {
        GetOrderByUserParams params = modelMapper.map(req,GetOrderByUserParams.class);
        AppResult<Page<OrderDTO>> result = orderService.getOrdersByUserId(params);
        if (result.isSuccess()) {
            Page<OrderDTO> orderDTOPage = result.getSuccess();

            return ResponseEntity.ok().body(orderDTOPage);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @Operation(summary = "Get order by course")
    @PostMapping("/course")
    public ResponseEntity<?> getOrderByCourse(@RequestBody ListOrderByCourseRequest req) {
        GetOrderCourseParams param = modelMapper.map(req,GetOrderCourseParams.class);

        AppResult<Page<OrderDTO>> result = orderService.getOrdersByCourseId(param);

        if (result.isSuccess()) {
            Page<OrderDTO> orderDTOPage = result.getSuccess();
            return ResponseEntity.ok().body(orderDTOPage);
        }

        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

}
