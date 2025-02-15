package aptech.project.educhain.domain.useCases.payment.order.getOrderByUserUseCase;

import lombok.Data;

@Data
public class GetOrderByUserParams {
    private String titleSearch;
    private int page;
    private int size;
    private int userId;
}
