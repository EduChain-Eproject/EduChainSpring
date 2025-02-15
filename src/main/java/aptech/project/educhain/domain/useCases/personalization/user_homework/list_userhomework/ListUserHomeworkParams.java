package aptech.project.educhain.domain.useCases.personalization.user_homework.list_userhomework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListUserHomeworkParams {
    private Integer userId;
    private Boolean isSubmitted;
    private int page;
    private int size;
}
