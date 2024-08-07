package aptech.project.educhain.domain.useCases.admin.get_userlist;

import lombok.Data;

@Data
public class GetListUserParams {
    private String nameSearch;
    private int page;
    private int size;
}
