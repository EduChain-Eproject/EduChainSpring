package aptech.project.educhain.endpoint.controllers.payment;

import aptech.project.educhain.data.serviceImpl.paypal.OrderService;
import aptech.project.educhain.data.serviceImpl.paypal.PaypalService;
import aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase.AddOrderParams;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/paypal")
public class PaymentController {
    private final PaypalService paypalService;
    private final OrderService orderService;

    @PostMapping("")
    public String pay(@RequestParam("sum") Float sum, @RequestParam Integer userId, @RequestParam Integer courseId) {
        try {
            Payment payment = paypalService.createPayment(
                    sum,
                    "USD",
                    "paypal",
                    "sale",
                    "course payment",
                    "http://localhost:8080/api/paypal/cancel",
                    "http://localhost:8080/api/paypal/success"
            );
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }

            AddOrderParams params = new AddOrderParams();
            params.setAmount(sum);
            params.setUserId(userId);
            params.setCourseId(courseId);
            orderService.addOrder(params);

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/success")
    public String success(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
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
