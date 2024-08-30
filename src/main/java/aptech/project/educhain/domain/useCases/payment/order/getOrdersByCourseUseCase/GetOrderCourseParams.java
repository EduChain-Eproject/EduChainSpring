package aptech.project.educhain.domain.useCases.payment.order.getOrdersByCourseUseCase;

import lombok.Data;

@Data
public class GetOrderCourseParams {
    private String titleSearch;
    private int page;
    private int size;
    private int courseId;
}
