package aptech.project.educhain.common;

import java.util.HashMap;
import java.util.Map;

public class ValidationError {
    private String type = "validation";  // Thêm trường type
    private Map<String, String> errors = new HashMap<>();

    public void addError(String field, String errorMessage) {
        errors.put(field, errorMessage);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public String getType() {
        return type;
    }
}