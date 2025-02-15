package aptech.project.educhain.endpoint.requests.payment.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private int page = 0;
    private int size = 10;
    private String titleSearch;
    private String sortBy = "createdAt";
}
