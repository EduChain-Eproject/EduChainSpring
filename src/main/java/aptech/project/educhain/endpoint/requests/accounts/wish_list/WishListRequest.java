package aptech.project.educhain.endpoint.requests.accounts.wish_list;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishListRequest {
    @NotNull(message = "cant recognize user")
        private int user_id;
    @NotNull(message = "cant recognize course")
    private int course_id;
}
