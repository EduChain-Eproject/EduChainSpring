package aptech.project.educhain.endpoint.requests.personaliztion.user_homework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHomeworkRequest {
    private Boolean isSubmitted;
    private int page;
    private int size;
}
