package aptech.project.educhain.endpoint.requests.personaliztion.user_interests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
    public class UserInterestRequest {
        private int student_id;
        private int page ;
        private int size ;
        private String titleSearch;
    }
