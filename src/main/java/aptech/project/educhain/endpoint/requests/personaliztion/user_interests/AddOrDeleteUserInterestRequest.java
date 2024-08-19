package aptech.project.educhain.endpoint.requests.personaliztion.user_interests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrDeleteUserInterestRequest {
    private int courseId;
}
