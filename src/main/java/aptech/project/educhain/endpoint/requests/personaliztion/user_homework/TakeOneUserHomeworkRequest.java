package aptech.project.educhain.endpoint.requests.personaliztion.user_homework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TakeOneUserHomeworkRequest {
    private int student_id;
    private int homework_id;
}
