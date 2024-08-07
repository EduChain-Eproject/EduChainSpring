package aptech.project.educhain.domain.useCases.payment.order.addOrderUseCase;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddOrderParams {
    private int userId;
    private int courseId;
    private BigDecimal amount;
}