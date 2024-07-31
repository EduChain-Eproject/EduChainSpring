package aptech.project.educhain.domain.useCases.payment.order.getAllOrderUseCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOrderParams {
    private int page;
    private int size;
    private String sortBy;
}
