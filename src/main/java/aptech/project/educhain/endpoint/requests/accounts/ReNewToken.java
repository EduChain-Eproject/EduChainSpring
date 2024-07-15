package aptech.project.educhain.endpoint.requests.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReNewToken {
    private String accessToken;
    private String refreshToken;
}
