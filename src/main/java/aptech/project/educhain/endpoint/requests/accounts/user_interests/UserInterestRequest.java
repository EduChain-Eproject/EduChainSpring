package aptech.project.educhain.endpoint.requests.accounts.user_interests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInterestRequest {
    @NotNull(message = "cant recognize user")
    private int student_id;
    @NotNull(message = "cant recognize course")
    private int course_id;
}
