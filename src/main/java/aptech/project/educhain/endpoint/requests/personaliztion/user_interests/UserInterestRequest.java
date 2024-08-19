package aptech.project.educhain.endpoint.requests.personaliztion.user_interests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
    public class UserInterestRequest {
        private int page = 0;
        private int size = 10;
        private String titleSearch;
    }
