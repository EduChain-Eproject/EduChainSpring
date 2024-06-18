package aptech.project.educhain.endpoint.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWithMessage<T> {
    private T object;
    private String message;

}
