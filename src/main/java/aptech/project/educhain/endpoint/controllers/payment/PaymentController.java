package aptech.project.educhain.endpoint.controllers.payment;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import aptech.project.educhain.common.result.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.data.serviceImpl.paypal.OrderService;
import aptech.project.educhain.data.serviceImpl.paypal.PaypalService;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.services.personalization.UserCourseService;
import aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase.AddOrderParams;
import aptech.project.educhain.domain.useCases.personalization.user_course.add_user_course.AddUserCourseParams;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/paypal")
public class PaymentController {
    private final PaypalService paypalService;
    private final OrderService orderService;
    private final CourseRepository courseRepository;

    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private IJwtService iJwtService;

    @PostMapping("payF/{courseId}")
    public ResponseEntity<?> payFlutter(@PathVariable Integer courseId, HttpServletRequest request) {
        try {
            User user = iJwtService.getUserByHeaderToken(request.getHeader("Authorization"));
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (courseOptional.isEmpty()) {
                ApiError apiError = new ApiError("Course not found");
                return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
            }
            Course course = courseOptional.get();
            var price = course.getPrice();

            String successUrl = "http://localhost:8080/api/paypal/success?sum="
                    + URLEncoder.encode(String.valueOf(price), StandardCharsets.UTF_8.toString()) + "&userId="
                    + URLEncoder.encode(String.valueOf(user.getId()), StandardCharsets.UTF_8.toString()) + "&courseId="
                    + URLEncoder.encode(String.valueOf(courseId), StandardCharsets.UTF_8.toString());

            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    "paypal",
                    "sale",
                    "course payment",
                    "http://localhost:8080/api/paypal/cancel",
                    successUrl);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return new ResponseEntity<>(link.getHref(), HttpStatus.OK);
                }
            }
            ApiError apiError = new ApiError("Approval URL not found");
            return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            ApiError apiError = new ApiError("PayPal error: " + e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ApiError apiError = new ApiError("Encoding error: " + e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/successF")
    @ResponseBody
    public String successFlutter(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
            @RequestParam double sum, @RequestParam Integer courseId, HttpServletRequest request) {

        try {
            User user = iJwtService.getUserByHeaderToken(request.getHeader("Authorization"));


            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                AddOrderParams params = new AddOrderParams();
                params.setAmount(BigDecimal.valueOf(sum));
                params.setUserId(user.getId());
                params.setCourseId(courseId);

                AddUserCourseParams addUserCourseParams = new AddUserCourseParams();
                addUserCourseParams.setCourse_id(courseId);
                addUserCourseParams.setStudent_id(user.getId());

                userCourseService.addUserCourseWithParams(addUserCourseParams);
                orderService.addOrder(params);

                String url = String.format("http://localhost:5173/courses/%d", courseId);
                return String.format("<a href=\"%s\">get back</a>", url);
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @PostMapping("pay/{courseId}")
    public String pay(@PathVariable Integer courseId, HttpServletRequest request) {
        User user = iJwtService.getUserByHeaderToken(request.getHeader("Authorization"));

        try {
            Course course = courseRepository.findById(courseId).get();
            var price = course.getPrice();

            String successUrl = "http://localhost:8080/api/paypal/success?sum="
                    + URLEncoder.encode(String.valueOf(price), StandardCharsets.UTF_8.toString()) + "&userId="
                    + URLEncoder.encode(String.valueOf(user.getId()), StandardCharsets.UTF_8.toString()) + "&courseId="
                    + URLEncoder.encode(String.valueOf(courseId), StandardCharsets.UTF_8.toString());

            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    "paypal",
                    "sale",
                    "course payment",
                    "http://localhost:8080/api/paypal/cancel",
                    successUrl);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return link.getHref();
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
    @ResponseBody
    public String success(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
                          @RequestParam double sum, @RequestParam Integer userId, @RequestParam Integer courseId) {

        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                AddOrderParams params = new AddOrderParams();
                params.setAmount(BigDecimal.valueOf(sum));
                params.setUserId(userId);
                params.setCourseId(courseId);

                AddUserCourseParams addUserCourseParams = new AddUserCourseParams();
                addUserCourseParams.setCourse_id(courseId);
                addUserCourseParams.setStudent_id(userId);

                userCourseService.addUserCourseWithParams(addUserCourseParams);
                orderService.addOrder(params);

                String url = String.format("http://localhost:5173/courses/%d", courseId);
                return String.format("<a href=\"%s\">get back</a>", url);
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
