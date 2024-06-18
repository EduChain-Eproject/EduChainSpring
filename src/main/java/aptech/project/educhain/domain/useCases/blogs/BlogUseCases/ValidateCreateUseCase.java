package aptech.project.educhain.domain.useCases.blogs.BlogUseCases;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidateCreateUseCase {
    public Map<String, String> execute(String title, Integer userId, Integer blogCategoryId, String blogText) {
        Map<String, String> errors = new HashMap<>();
        if (title == null || title.isEmpty()) {
            errors.put("title", "Title is required");
        }
        if (userId == null) {
            errors.put("userId", "User ID is required");
        }
        if (blogCategoryId == null) {
            errors.put("blogCategoryId", "Blog Category ID is required");
        }
        if (blogText == null || blogText.isEmpty()) {
            errors.put("blogText", "Blog Text is required");
        }
        return errors;
    }
}
