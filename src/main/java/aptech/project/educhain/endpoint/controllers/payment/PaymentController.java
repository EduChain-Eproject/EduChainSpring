package aptech.project.educhain.endpoint.controllers.payment;

import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.data.serviceImpl.courses.CourseService;
import aptech.project.educhain.data.serviceImpl.paypal.OrderService;
import aptech.project.educhain.data.serviceImpl.paypal.PaypalService;
import aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase.AddOrderParams;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/paypal")
public class PaymentController {
    private final PaypalService paypalService;
    private final OrderService orderService;
    private final CourseRepository courseRepository;

    @PostMapping("")
    public String pay(@RequestParam Integer userId, @RequestParam Integer courseId) {
        try {
            Course course = courseRepository.findById(courseId).get();
            var price = course.getPrice();

            String successUrl = "http://localhost:8080/api/paypal/success?sum=" + URLEncoder.encode(String.valueOf(price), StandardCharsets.UTF_8.toString()) + "&userId=" + URLEncoder.encode(String.valueOf(userId), StandardCharsets.UTF_8.toString()) + "&courseId=" + URLEncoder.encode(String.valueOf(courseId), StandardCharsets.UTF_8.toString());

            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    "paypal",
                    "sale",
                    "course payment",
                    "http://localhost:8080/api/paypal/cancel",
                    successUrl
            );
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

    @GetMapping("/success")
    public String success(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam double sum, @RequestParam Integer userId, @RequestParam Integer courseId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                AddOrderParams params = new AddOrderParams();
                params.setAmount(BigDecimal.valueOf(sum));
                params.setUserId(userId);
                params.setCourseId(courseId);
                orderService.addOrder(params);

                return "success";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "cancel";
    }
}
