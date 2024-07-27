package aptech.project.educhain.endpoint.requests.personaliztion.user_award;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardRequest {
    int page;
    int size;
    int userId;
}
