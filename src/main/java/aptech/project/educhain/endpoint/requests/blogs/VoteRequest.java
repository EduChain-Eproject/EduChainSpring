package aptech.project.educhain.endpoint.requests.blogs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {
    private Integer userId;
    private Integer blogId;
    private Integer vote;
}
