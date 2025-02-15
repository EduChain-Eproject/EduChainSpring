package aptech.project.educhain.common.result;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ApiError {
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public ApiError(Map<String, String> errors) {
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public ApiError(String error) {
        this.timestamp = LocalDateTime.now();
        this.errors = new HashMap<>();
        this.errors.put("message", error);
    }
}
